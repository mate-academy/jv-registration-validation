package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int AGE_GUINNESS_RECORD = 122;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final String NUMBER_LETTERS_PATTERN = "[a-zA-Z0-9]+";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        String login = user.getLogin();
        String password = user.getPassword();
        validateLogin(login);
        validatePassword(login, password);
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    public void validateUser(User user) {
        if (user == null) {
            throw new InvalidInputException("User can't be null");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidInputException("Login can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidInputException("Login should be larger than " + MIN_LOGIN_LENGTH);
        }
        if (login.length() > MAX_LOGIN_LENGTH) {
            throw new InvalidInputException("Login size should be less than " + MAX_LOGIN_LENGTH);
        }
        if (!login.matches(NUMBER_LETTERS_PATTERN)) {
            throw new InvalidInputException("Login can't contain special characters: @#$%");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidInputException("A user with this login already exists");
        }
    }

    private void validatePassword(String login, String password) {
        if (password == null) {
            throw new InvalidInputException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidInputException("Password must be less than " + MAX_PASSWORD_LENGTH + " characters long");
        }
        if (password.equals(login)) {
            throw new InvalidInputException("Password must be different from login");
        }
        if (password.matches(System.lineSeparator())) {
            throw new InvalidInputException("Password can't contain new line separator");
        }
    }

    private void validateAge(int age) {
        if (age < MIN_AGE) {
            throw new InvalidInputException("Age must be at least " + MIN_AGE);
        }
        if (age >= AGE_GUINNESS_RECORD) {
            throw new InvalidInputException("Age must be less than " + AGE_GUINNESS_RECORD);
        }
    }
}
