package core.basesyntax.service;

import core.basesyntax.customexception.ValidationExceptionIncorrectValue;
import core.basesyntax.customexception.ValidationExceptionNullValue;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            try {
                if (!isLoginValid(user)) {
                    throw new ValidationExceptionIncorrectValue("Login is incorrect");
                } else if (storageDao.get(user.getLogin()) != null) {
                    throw new ValidationExceptionIncorrectValue("Login is already registered");
                }
            } catch (NullPointerException e) {
                throw new ValidationExceptionNullValue("User login can't be null " + e);
            }
            try {
                if (!isAgeValid(user)) {
                    throw new ValidationExceptionIncorrectValue(
                            "Must meet the minimum age requirement");
                }
            } catch (NullPointerException e) {
                throw new ValidationExceptionNullValue("User age can't be null " + e);
            }
            try {
                if (!isPasswordValid(user)) {
                    throw new ValidationExceptionIncorrectValue(
                            "Must be at least 6 characters without whitespace");
                }
            } catch (NullPointerException e) {
                throw new ValidationExceptionNullValue("User password can't be null " + e);
            }
        } else {
            throw new ValidationExceptionNullValue("User can't be null ");
        }
        return storageDao.add(user);
    }

    private boolean isLoginValid(User user) {
        return !user.getLogin().isEmpty() && !user.getLogin().contains(" ");
    }

    private boolean isAgeValid(User user) {
        return user.getAge() >= USER_MIN_AGE;
    }

    private boolean isPasswordValid(User user) {
        return !user.getPassword().isEmpty() && !user.getPassword().contains(" ")
                && user.getPassword().length() >= USER_PASSWORD_MIN_LENGTH;
    }
}
