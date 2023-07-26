package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_VALID_LENGTH = 6;
    public static final int MINIMUM_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userCheck(user);
        loginCheck(user);
        passwordCheck(user);
        ageCheck(user);
        return storageDao.add(user);
    }

    private void userCheck(User user) {
        if (user == null) {
            throw new RegistrationException("The entered data is incorrect. "
                    + "The user may not exist.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The entered data is incorrect. "
                    + "Perhaps the user already exists.");
        }
    }

    private void loginCheck(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Please enter a login");
        }
        if (user.getLogin().length() < MINIMUM_VALID_LENGTH) {
            throw new RegistrationException("The login " + user.getLogin()
                    + " is incorrect.");
        }
    }

    private void passwordCheck(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Please enter a password");
        }
        if (user.getPassword().length() < MINIMUM_VALID_LENGTH) {
            throw new RegistrationException("The password " + user.getPassword()
                    + " is incorrect.");
        }
    }

    private void ageCheck(User user) {
        if (user.getAge() < MINIMUM_VALID_AGE) {
            throw new RegistrationException("The user is underage.");
        }
    }
}
