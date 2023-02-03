package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 1;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_YEARS_OLD = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        userLoginValidation(user);
        userAgeValidation(user);
        userPasswordValidation(user);
        storageDao.add(user);
        return user;
    }

    private void userValidation(User user) {
        if (user == null) {
            throw new UserValidationException("User can't be null");
        }
    }

    private void userLoginValidation(User user) {
        if (user.getLogin() == null) {
            throw new UserValidationException("Login of user can't be null");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new UserValidationException("Login must have at least one symbol");
        }
        for (User userData : Storage.people) {
            if (userData.getLogin().equals(user.getLogin())) {
                throw new UserValidationException("This login exist. Please set another login");
            }
        }
    }

    private void userPasswordValidation(User user) {
        if (user.getPassword() == null) {
            throw new UserValidationException("Password mustn't be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new UserValidationException("Password must be at least 6 characters");
        }
    }

    private void userAgeValidation(User user) {
        if (user.getAge() == null) {
            throw new UserValidationException("The user age mustn't be null");
        }
        if (user.getAge() < MINIMUM_YEARS_OLD) {
            throw new UserValidationException("The user age must be at least 18 years old");
        }
    }
}
