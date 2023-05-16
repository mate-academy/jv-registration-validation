package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
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
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age can not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("Age shouldn't be less than " + MIN_AGE
                    + " ,but you entered: " + user.getAge());
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can not be null");
        }
        if (user.getPassword().isBlank()) {
            throw new UserRegistrationException("The password cannot consist of spaces");
        }
        if (user.getPassword().length() < MIN_COUNT_OF_CHAR) {
            throw new UserRegistrationException("Password shouldn't be less than "
                    + MIN_COUNT_OF_CHAR);
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can't be null");
        }
        if (user.getLogin().isBlank()) {
            throw new UserRegistrationException("The login cannot consist of spaces");
        }
        if (user.getLogin().length() < MIN_COUNT_OF_CHAR) {
            throw new UserRegistrationException("Login shouldn't be less than "
                    + MIN_COUNT_OF_CHAR);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("A user already exists with this login");
        }
    }
}
