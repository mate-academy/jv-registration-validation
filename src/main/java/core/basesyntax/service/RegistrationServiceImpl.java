package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Login cannot be empty");
        }
        if (login.length() < 6) {
            throw new RegistrationException("user's login is at least 6 characters");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login is already taken");
        }
        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password cannot be empty");
        }
        if (password.length() < 6) {
            throw new RegistrationException("user's password is at least 6 characters");
        }
        Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Age cannot be empty");
        }
        if (age < 18) {
            throw new RegistrationException("user's age is at least 18 years old");
        }
        return storageDao.add(user);
    }
}
