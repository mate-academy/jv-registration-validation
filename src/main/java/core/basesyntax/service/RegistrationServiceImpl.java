package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DATA_CORRECT_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        throwNullException(user, "User");
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void checkAge(int age) {
        if (age < MINIMUM_AGE) {
            throw new FailedRegistrationException("Dear user, you're too young. "
                    + "Please, come back in " + (MINIMUM_AGE - age));
        }
        if (age > MAXIMUM_AGE) {
            throw new FailedRegistrationException("Dear user, we can't believe,"
                    + " that you are so old.");
        }
    }

    private void checkPassword(String password) {
        throwNullException(password, "Password");
        if (password.length() < DATA_CORRECT_LENGTH) {
            throw new FailedRegistrationException(getExceptionMessage(password, "Password"));
        }
    }

    private void checkLogin(String login) {
        throwNullException(login, "Login");
        if (login.length() < DATA_CORRECT_LENGTH) {
            throw new FailedRegistrationException(getExceptionMessage(login, "Login"));
        } else if (storageDao.get(login) != null) {
            throw new FailedRegistrationException("The user with login \'"
                    + login + "\' already exist.");
        }
    }

    private String getExceptionMessage(String inputData, String type) {
        return type + " is too short, actual size = "
                + inputData.length() + ", it should be at least " + DATA_CORRECT_LENGTH;
    }

    private void throwNullException(Object object, String type) {
        if (object == null) {
            throw new NullPointerException(type + " can't be null.");
        }
    }
}
