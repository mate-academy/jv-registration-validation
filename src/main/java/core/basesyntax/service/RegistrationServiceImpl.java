package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.CurrentLoginIsExistsException;
import core.basesyntax.model.InvalidInputDataException;
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
        loginCheck(user);
        passwordCheck(user);
        checkAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Field age can't be empty or null");
        }
        if (user.getAge() < AGE_LIMIT) {
            throw new InvalidInputDataException("You can't register until 18 years old. Sorry(");
        }
    }

    private void passwordCheck(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidInputDataException("Field password can't be empty or null");
        }
        if (user.getPassword().length() <= PASSWORD_MIN_LENGTH) {
            throw new InvalidInputDataException("Your password must contain more than 10 elements");
        }
        if (!(user.getPassword().matches(PATTERN_FOR_PASSWORD_LOGIN))) {
            throw new InvalidInputDataException("Your password must much pattern [a-z-A-Z-0-9]");
        }
    }

    private void loginCheck(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidInputDataException("Field Login can't be empty or null. Try again");
        }
        if (user.getLogin() != null && storageDao.get(user.getLogin()) != null) {
            throw new CurrentLoginIsExistsException("The current login is occupied"
                    + " by another user. Please try again");
        }
        if (user.getLogin().length() <= LOGIN_MIN_LENGTH) {
            throw new InvalidInputDataException("Your login must contain more than 14 elements");
        }
        if (!(user.getLogin().matches(PATTERN_FOR_PASSWORD_LOGIN))) {
            throw new InvalidInputDataException("Your login must much pattern [a-z-A-Z-0-9]");
        }
    }

    private static void nullCheck(User user) {
        if (user == null) {
            throw new InvalidInputDataException("Inserted user data not exists");
        }
    }
}
