package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        boolean isUserValid = validateUser(user);
        if (isUserValid) {
            return storageDao.add(user);
        }
        return null;
    }

    private boolean validateUser(User user) {
        if (user == null) {
            return false;
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || password == null || age == null) {
            return false;
        }
        if (login.length() < 6 || password.length() < 6 || age < 18) {
            return false;
        }
        User existingUser = storageDao.get(login);
        return existingUser == null;
    }
}
