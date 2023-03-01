package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.CurrentLoginIsExists;
import core.basesyntax.model.InvalidInputData;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_LIMIT = 18;
    private static final int LOGIN_MIN_LENGTH = 14;
    private static final int PASSWORD_MIN_LENGTH = 10;
    private static final String PATTERN_FOR_PASSWORD_LOGIN = "[A-Za-z0-9]*";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        nullCheck(user);
        loginNullOrEmptyCheck(user);
        loginExistenceCheck(user);
        passwordNullOrEmptyCheck(user);
        ageNullCheck(user);
        loginLengthCheck(user);
        loginPatternCheck(user);
        passwordLengthCheck(user);
        passwordPatternCheck(user);
        ageValidation(user);
        storageDao.add(user);
        return user;
    }

    private static void ageNullCheck(User user) {
        if (user.getAge() == null) {
            throw new InvalidInputData("Field age can't be empty or null");
        }
    }

    private static void ageValidation(User user) {
        if (user.getAge() < AGE_LIMIT) {
            throw new InvalidInputData("You can't register until 18 years old. Sorry(");
        }
    }

    private static void passwordPatternCheck(User user) {
        if (!(user.getPassword().matches(PATTERN_FOR_PASSWORD_LOGIN))) {
            throw new InvalidInputData("Your password must much pattern [a-z-A-Z-0-9]");
        }
    }

    private static void passwordLengthCheck(User user) {
        if (user.getPassword().length() <= PASSWORD_MIN_LENGTH) {
            throw new InvalidInputData("Your password must contain more than 10 elements");
        }
    }

    private static void passwordNullOrEmptyCheck(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidInputData("Field password can't be empty or null");
        }
    }

    private static void loginPatternCheck(User user) {
        if (!(user.getLogin().matches(PATTERN_FOR_PASSWORD_LOGIN))) {
            throw new InvalidInputData("Your login must much pattern [a-z-A-Z-0-9]");
        }
    }

    private static void loginLengthCheck(User user) {
        if (user.getLogin().length() <= LOGIN_MIN_LENGTH) {
            throw new InvalidInputData("Your login must contain more than 14 elements");
        }
    }

    private static void loginNullOrEmptyCheck(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidInputData("Field Login can't be empty or null. Try again");
        }
    }

    private void loginExistenceCheck(User user) {
        if (user.getLogin() != null && storageDao.get(user.getLogin()) != null) {
            throw new CurrentLoginIsExists("The current login is occupied"
                    + " by another user. Please try again");
        }
    }

    private static void nullCheck(User user) {
        if (user == null) {
            throw new InvalidInputData("Inserted user data not exists");
        }
    }
}
