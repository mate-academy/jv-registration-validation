package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    /*

    there is no user with such login in the Storage yet
    user's login is at least 6 characters
    user's password is at least 6 characters
    user's age is at least 18 years old

    */
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int DEFAULT_USER_LOGIN_SIZE = 6;
    private static final int DEFAULT_USER_PASSWORD_SIZE = 6;
    private static final int MINIMUM_USER_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getPassword() == null) {
            throw new InvalidRegistrationDataException("Password cannot be null!");
        }
        if (user.getPassword().isEmpty()) {
            throw new InvalidRegistrationDataException("Password cannot be empty!");
        }
        if (user.getLogin() == null) {
            throw new InvalidRegistrationDataException("Login cannot be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidRegistrationDataException("Login cannot be empty!");
        }
        if (user.getAge() == 0) {
            throw new InvalidRegistrationDataException("Age must be greater that 0");
        }
        if (user.getAge() < 0) {
            throw new InvalidRegistrationDataException("Age cannot be negative number!");
        }
        if (user.getLogin().length() < DEFAULT_USER_LOGIN_SIZE) {
            throw new InvalidRegistrationDataException("Login must be at least " + DEFAULT_USER_LOGIN_SIZE + " characters long!");
        }
        if (user.getPassword().length() < DEFAULT_USER_PASSWORD_SIZE) {
            throw new InvalidRegistrationDataException("Password must be at least " + DEFAULT_USER_PASSWORD_SIZE + " characters long!");
        }
        if (user.getAge() < MINIMUM_USER_AGE) {
            throw new InvalidRegistrationDataException("Not valid age: " + user.getAge() + ". Min allowed age is " + MINIMUM_USER_AGE);
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        } else {
            throw new InvalidRegistrationDataException("User with login " + user.getLogin() + " already exist!");
        }
        return user;
    }
}
