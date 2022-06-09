package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int ZERO_AGE = 0;
    private static final int UNBELIEVEBLE_AGE = 130;
    private static final int EMPTY_LINE = 0;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with input login "
                    + "already present in storage, choose another login");
        } else {
            storageDao.add(user);
            return user;
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age is empty, input your age");
        }
        if (user.getAge() < VALID_AGE && user.getAge() > ZERO_AGE) {
            throw new RuntimeException("Age for access must be 18 and older");
        }
        if (user.getAge() < ZERO_AGE) {
            throw new RuntimeException("Age can't be less then zero");
        }
        if (user.getAge() > UNBELIEVEBLE_AGE) {
            throw new RuntimeException("We don't believe in this age");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is empty, input your login");
        }
        if (user.getLogin().length() == EMPTY_LINE || user.getLogin().matches("^[ ]*$")) {
            throw new RuntimeException("Login is empty, input your login");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new RuntimeException("First letter of login must be a letter");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is empty, input you password");
        }

        if (user.getPassword().length() == EMPTY_LINE || user.getPassword().matches("^[ ]*$")) {
            throw new RuntimeException("Password is empty, input you password");
        }

        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Password is short, input at least 6 character");
        }
    }
}
