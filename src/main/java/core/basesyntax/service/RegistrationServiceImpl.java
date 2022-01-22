package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storage = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {

        if (user == null || user.getLogin() == null || user.equals(storage.get(user.getLogin()))) {
            throw new RuntimeException("Add user failed");
        }

        if (user.getAge() < 18) {
            throw new RuntimeException("User age is not acceptable");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password should be at least  6 characters");
        }

        storage.add(user);
        return storage.get(user.getLogin());
    }

    public User get(String login) {
        return storage.get(login);
    }
}
