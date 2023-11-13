package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        checkUserDuplicated(user);
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("The login can't be of null value.");
        }
        if (login.length() < MINIMUM_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid login's length. It must contain "
                    + MINIMUM_LOGIN_LENGTH + " or more characters.");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("The password can't be of null value.");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid password's length. It must contain "
                    + MINIMUM_PASSWORD_LENGTH + " or more characters.");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("The age can't be of null value.");
        }
        if (age < MINIMUM_AGE) {
            throw new RegistrationException("The age can't be less than " + MINIMUM_AGE + ".");
        }
    }

    private void checkUserDuplicated(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User " + user.getLogin() + " is already registered.");
        }
    }
}
