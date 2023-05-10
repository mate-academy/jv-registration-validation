package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.MyValidatorException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_COUNT_OF_CHAR = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new MyValidatorException("Age can not be null");
        }
        if (user.getAge() <= 0) {
            throw new MyValidatorException("Age cannot be equal to or less than 0"
                    + " ,but you entered: " + user.getAge());
        }
        if (user.getAge() < MIN_AGE) {
            throw new MyValidatorException("Age cannot be less than " + MIN_AGE
                    + " ,but you entered: " + user.getAge());
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new MyValidatorException("Password can not be null");
        }
        if (user.getPassword().isBlank()) {
            throw new MyValidatorException("The password cannot consist of spaces");
        }
        if (user.getPassword().length() < MIN_COUNT_OF_CHAR) {
            throw new MyValidatorException("Password can't fewer less symbol than "
                    + MIN_COUNT_OF_CHAR);
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new MyValidatorException("Login can't be null");
        }
        if (user.getLogin().isBlank()) {
            throw new MyValidatorException("The login cannot consist of spaces");
        }
        if (user.getLogin().length() < MIN_COUNT_OF_CHAR) {
            throw new MyValidatorException("Login can't fewer less symbols than "
                    + MIN_COUNT_OF_CHAR);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new MyValidatorException("A user already exists with this login");
        }
    }
}

