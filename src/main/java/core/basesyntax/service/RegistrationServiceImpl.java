package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validUser(user);
        validLogin(user);
        validPassword(user);
        validAge(user);
        return storageDao.add(user);
    }

    public void validUser(User user) {
        if (user == null) {
            throw new ValidationException("User can't be equaled null");
        }
    }

    public void validLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("The data base contains user with such login. "
                    + "Please change your login.");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be equaled null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("Login length can't be less, than "
                    + MIN_LOGIN_LENGTH + " characters.");
        }
    }

    public void validPassword(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be equaled null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password length can't be less, than "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    public void validAge(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("Age can't be equaled null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new ValidationException("Age can't be less than "
                    + MIN_USER_AGE + ".");
        }
    }
}
