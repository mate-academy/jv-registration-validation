package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataForRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 130;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_FOR_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserForNull(user);
        checkFieldsForNull(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        checkLoginForAlreadyExistingValue(user);
        return storageDao.add(user);
    }

    private void checkUserForNull(User user) {
        if (user == null) {
            throw new InvalidDataForRegistrationException("User can't be null");
        }
    }

    private void checkFieldsForNull(User user) {
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            throw new InvalidDataForRegistrationException("Can't register "
                    + "user with null age/login/password");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new InvalidDataForRegistrationException("Can't register "
                    + "user with age less than 18 and greater than 130");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().equals("")) {
            throw new InvalidDataForRegistrationException("Can't register "
                    + "user with empty login");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH_FOR_PASSWORD) {
            throw new InvalidDataForRegistrationException("Your password "
                    + "must contain at least six characters");
        }
    }

    private void checkLoginForAlreadyExistingValue(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataForRegistrationException("User with "
                    + "this login already exists");
        }
    }
}
