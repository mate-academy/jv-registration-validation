package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String DIGITS = "(.*)\\d(.*)";
    private static final String UPPERCASE_LETTERS = "(.*)[A-Z](.*)";
    private static final String WHITESPACE = " ";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login field can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login field can't be empty");
        }
        if (user.getLogin().contains(WHITESPACE)) {
            throw new RuntimeException("Login can't contains whitespaces");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already exist");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("Password field can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be at least 6");
        }
        if (user.getPassword().contains(WHITESPACE)) {
            throw new RuntimeException("Password can not contain whitespace");
        }
        if (!user.getPassword().matches(DIGITS)) {
            throw new RuntimeException("Password must contain at least one digit");
        }
        if (!user.getPassword().matches(UPPERCASE_LETTERS)) {
            throw new RuntimeException("Password must contain at least one uppercase letter");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age field can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be at least 18");
        }
    }
}
