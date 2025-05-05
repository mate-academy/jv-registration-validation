package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserNotNull(user);
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        userExists(user);
        return storageDao.add(user);
    }

    private boolean checkPassword(String password) {
        if (password == null) {
            throw new UserRegistrationException("Password age, password cannot be null");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Invalid password , password shoudnt be less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        return true;
    }

    private boolean checkAge(Integer age) {
        if (age == null) {
            throw new UserRegistrationException("Invalid age, age cannot be null");
        } else if (age < MIN_AGE) {
            throw new UserRegistrationException("Invalid age, age shoudnt be less than "
                    + MIN_AGE);
        }
        return true;
    }

    private boolean checkLogin(String login) {
        if (login == null) {
            throw new UserRegistrationException("Invalid login, login cannot be null");
        } else if (login.length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationException("Invalid login , login shoudnt be less than "
            + MIN_LOGIN_LENGTH + " characters");
        }
        return true;
    }

    private boolean userExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User already exists, please choose other one");
        }
        return false;
    }

    private boolean validateUserNotNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("User cannot be null");
        }
        return false;
    }
}
