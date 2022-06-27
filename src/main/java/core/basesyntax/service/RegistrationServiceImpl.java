package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be null");
        }
        if (isLoginUnique(user) && isUserAdult(user) && isPasswordLengthOk(user)) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean isLoginUnique(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("This login is already exist."
                    + "Please, create unique login.");
        }
        return true;
    }

    private boolean isUserAdult(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new ValidationException("Authorization is available only if you are over 18.");
        }
        return true;
    }

    private boolean isPasswordLengthOk(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Your password should contain more than 6 symbols.");
        }
        return true;
    }
}
