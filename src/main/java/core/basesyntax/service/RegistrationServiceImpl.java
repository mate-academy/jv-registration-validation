package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ID_LENGTH = 1;
    private static final int MAX_ID_LENGTH = 200;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 24;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 110;
    private static final String SPACE = " ";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    User validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
        return user;
    }

    Long validateUserId(Long id) {
        if (id == null) {
            throw new RegistrationException("You entered an incorrect ID");
        }
        if (id < MIN_ID_LENGTH) {
            throw new RegistrationException("Invalid User ID length (minimum)!");
        }
        if (id > MAX_ID_LENGTH) {
            throw new RegistrationException("Invalid User ID length (maximum)!");
        }
        return id;
    }

    public String validateUserLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Incorrect Login: Login cannot be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid User Login length (min): " + login.length());
        }
        if (login.length() > MAX_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid User Login length (max): " + login.length());
        }
        if (login.contains(SPACE)) {
            throw new RegistrationException("User login cannot contain spaces.  " + login);
        }

        return login;
    }

    String validateUserPassword(String password) {
        if (password == null) {
            throw new RegistrationException("User password cannot be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid User password length."
                    + " Your password is shorter than the minimum length : "
                    + password.length());
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid User password length."
                    + " Your password exceeds the maximum length : " + password.length());
        }
        if (!password.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) {
            throw new RegistrationException("User password must contain at least one special symbol"
                    + password);
        }
        return password;
    }

    void validateUserAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("User age cannot be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Invalid User age range :" + age);
        }

        if (age > MAX_AGE) {
            throw new RegistrationException("Invalid User age range : " + age);
        }
    }
}

