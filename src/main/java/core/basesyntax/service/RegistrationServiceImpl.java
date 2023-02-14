package core.basesyntax.service;

import core.basesyntax.custom.exception.UserValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String msg = checkForNullValues(user);
        if (!msg.equals("OK")) {
            throw new UserValidationException(msg);
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("Login is already registered");
        } else if (!isLoginValid(user)) {
            throw new UserValidationException(
                    "Can't be empty or contain whitespace");
        } else if (!isAgeValid(user)) {
            throw new UserValidationException(
                    "Must meet the minimum age requirement");
        } else if (!isPasswordValid(user)) {
            throw new UserValidationException(
                    "Must be at least 6 characters without whitespace");
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

    private String checkForNullValues(User user) {
        if (user == null) {
            return "User can't be null";
        } else if (user.getLogin() == null) {
            return "User can't be null";
        } else if (user.getPassword() == null) {
            return "Password can't be null";
        } else if (user.getAge() == null) {
            return "Age can't be null";
        }
        return "OK";
    }
}
