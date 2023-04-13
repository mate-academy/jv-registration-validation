package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String USER_ALREADY_EXIST =
            "User already exist with login - %s ";
    private static final String USER_LOGIN_EXCEPTION =
            "User login must be at least 6 characters current login length - %s ";
    private static final String USER_PASSWORD_EXCEPTION =
            "User password must be at least 6 characters current password length - %s ";
    private static final String USER_AGE_EXCEPTION =
             "User age must be at least 18 current age - %s ";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isUserExist(user)) {
            throw new ValidationException(String.format(
                    USER_ALREADY_EXIST, user.getLogin()));
        }
        if (!isUserLoginCorrect(user)) {
            throw new ValidationException(String.format(
                    USER_LOGIN_EXCEPTION, user.getLogin().length()));
        }
        if (!isUserPasswordCorrect(user)) {
            throw new ValidationException(String.format(
                    USER_PASSWORD_EXCEPTION, user.getPassword().length()));
        }
        if (!isUserAgeCorrect(user)) {
            throw new ValidationException(String.format(
                    USER_AGE_EXCEPTION, user.getAge()));
        }
        return storageDao.add(user);
    }

    private boolean isUserExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean isUserLoginCorrect(User user) {
        return user.getLogin().length() >= 6;
    }

    private boolean isUserPasswordCorrect(User user) {
        return user.getPassword().length() >= 6;
    }

    private boolean isUserAgeCorrect(User user) {
        return user.getAge() >= 18;
    }
}
