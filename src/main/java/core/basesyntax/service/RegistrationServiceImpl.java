package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new UserRegistrationException("User shouldn't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User: " + user.getLogin() + " already exists!");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login shouldn't be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new UserRegistrationException("Login shouldn't be empty!");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Login must contain at least "
                    + MIN_LENGTH + " character");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age shouldn't be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("Age shouldn't be less than "
                    + MIN_AGE + " years!");
        }
        if (user.getAge() > MAX_AGE) {
            throw new UserRegistrationException("Age shouldn't be more than "
                    + MAX_AGE + " years!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password shouldn't be null!");
        }
        if (user.getPassword().isEmpty()) {
            throw new UserRegistrationException("Password shouldn't be empty!");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Password shouldn't be less than "
                    + MIN_LENGTH + " characters!");
        }
    }
}
