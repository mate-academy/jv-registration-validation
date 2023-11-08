package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationUserException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationUserException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationUserException(
                    "Login length must be at least " + MIN_LOGIN_LENGTH + " characters long. "
                            + "Provided login has only " + user.getLogin()
                            + " character(s) length");
        }
        if (user.getPassword() == null) {
            throw new RegistrationUserException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationUserException(
                    "Password length must at least " + MIN_PASSWORD_LENGTH
                            + " characters. Provided password has only " + user.getPassword()
                            + " character(s) length");
        }
        if (user.getAge() == null) {
            throw new RegistrationUserException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationUserException(
                    "Provided age " + user.getAge() + "is not valid. Minimal allowed user's age is "
                            + MIN_AGE);
        }
        if (!Storage.people.isEmpty() && storageDao.get(user.getLogin()).equals(user)) {
            throw new RegistrationUserException(
                    "User with login " + "\"" + user.getLogin() + "\"" + " " + "already exists");
        }
        return storageDao.add(user);
    }
}
