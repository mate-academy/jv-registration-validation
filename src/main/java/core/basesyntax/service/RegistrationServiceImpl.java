package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 4;
    private static final int INPUT_MAX_LENGTH = 16;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MIN = 18;
    private static final int AGE_MAX = 140;
    private static final String LOGIN_MATCH_REGEX = "\\w++";

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void checkForNulls(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new InvalidUserInputException("User params can't be null");
        }
    }

    private void checkLogin(String login) {
        if (login.length() < LOGIN_MIN_LENGTH || login.length() > INPUT_MAX_LENGTH
                || !login.matches(LOGIN_MATCH_REGEX)) {
            throw new InvalidUserInputException("Invalid login");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserInputException("User already exists");
        }
    }

    private void checkPassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH
                || password.length() > INPUT_MAX_LENGTH) {
            throw new InvalidUserInputException("Invalid password");
        }
    }

    private void checkAge(Integer age) {
        if (age < AGE_MIN || age > AGE_MAX) {
            throw new InvalidUserInputException("Invalid age");
        }
    }
}
