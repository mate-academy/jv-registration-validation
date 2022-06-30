package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_USER_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (isLoginUnique(user) && isUserAdult(user) && isPasswordLengthOk(user)) {
            return storageDao.add(user);
        }
        throw new RuntimeException("User data are invalid or contain irrelevant data types."
                + " Please make sure you enter everything correct.");
    }

    private boolean isLoginUnique(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("This login is already exist."
                    + "Please, create unique login.");
        }
        return true;
    }

    private boolean isUserAdult(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new ValidationException("Authorization is available only if you are over "
                    + MIN_USER_AGE);
        }
        return true;
    }

    private boolean isPasswordLengthOk(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Your password should contain more than "
                    + MIN_PASSWORD_LENGTH + " symbols.");
        }
        return true;
    }
}
