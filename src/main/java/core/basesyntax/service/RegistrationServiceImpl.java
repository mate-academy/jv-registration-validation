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
    private static final int MIN_USER_LOGIN_SIZE = 6;
    private static final int MIN_USER_PASSWORD_SIZE = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null!");
        }
        if (user.getPassword().isEmpty()) {
            throw new
                    RegistrationException("Password cannot be empty!");
        }
        if (user.getLogin() == null) {
            throw new
                    RegistrationException("Login cannot be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new
                    RegistrationException("Login cannot be empty!");
        }
        if (user.getLogin().length() < MIN_USER_LOGIN_SIZE) {
            throw new
                    RegistrationException("Login must be at least "
                    + MIN_USER_LOGIN_SIZE + " characters long!");
        }
        if (user.getPassword().length() < MIN_USER_PASSWORD_SIZE) {
            throw new
                    RegistrationException("Password must be at least "
                    + MIN_USER_PASSWORD_SIZE + " characters long!");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new
                    RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_USER_AGE);
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        } else {
            throw new
                    RegistrationException("User with login "
                    + user.getLogin() + " already exist!");
        }
        return user;
    }
}
