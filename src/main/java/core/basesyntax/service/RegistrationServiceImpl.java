package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationFailException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isValid(user)) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean isValid(User user) throws RegistrationFailException {
        if (user == null) {
            throw new RegistrationFailException("You didn't insert any data!");
        }
        return isLoginValid(user.getLogin())
                && isPasswordValid(user.getPassword())
                && isAgeValid(user.getAge())
                && !isLoginInStorage(user);
    }

    private boolean isLoginValid(String login) {
        if (login == null) {
            throw new RegistrationFailException("Your login is null!!!");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationFailException("Minimal login length is "
                    + MIN_LOGIN_LENGTH + "!");
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password == null) {
            throw new RegistrationFailException("Your password is null!!!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationFailException("Minimal password length is "
                    + MIN_PASSWORD_LENGTH + "!");
        }
        return true;
    }

    private boolean isAgeValid(Integer age) {
        if (age == null) {
            throw new RegistrationFailException("Your age is null!!!");
        }
        if (age < MIN_AGE) {
            throw new RegistrationFailException("Minimal age for registration is " + MIN_AGE + "!");
        }
        return true;
    }

    private boolean isLoginInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailException("Your login is already used.");
        }
        return false;
    }
}
