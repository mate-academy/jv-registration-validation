package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        int age = user.getAge();

        if (login == null) {
            throw new RegisterException("This isnt login");
        }
        if (password == null) {
            throw new RegisterException("this isnt password");
        }
        if (age < 18) {
            throw new RegisterException("Ur age is too low");
        }
        if (password.length() < 6) {
            throw new RegisterException("Password is too short");
        }
        if (login.length() < 6) {
            throw new RegisterException("Login is too short");
        }
        if (user.getLogin()
                .equals(storageDao.get(login).getLogin())) {
            throw new RegisterException("We have already this login");
        }

        return storageDao.add(user);
    }
}
