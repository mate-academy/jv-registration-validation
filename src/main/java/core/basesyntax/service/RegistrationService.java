package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationService implements RegistrationServiceInterface {
    public static final int VALID_LOGIN_LENGTH = 6;
    public static final int VALID_PASSWORD_LENGTH = 6;
    public static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user value can't be null");
        }

        checkLoginValid(user);
        checkPasswordValid(user);
        checkAgeAllowed(user);
        checkUserAlreadyExist(user);

        return storageDao.add(user);
    }

    public void checkUserAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(String.format(
                    "User with %s as a login already exist", user.getLogin()));
        }
    }

    public void checkLoginValid(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("The login can't be null");
        }

        int loginLength = user.getLogin().trim().length();
        if (loginLength < VALID_LOGIN_LENGTH) {
            throw new RegistrationException(
                    String.format("The login must have at least %d symbols, but you have: %d",
                            VALID_LOGIN_LENGTH, loginLength));
        }
    }

    public void checkPasswordValid(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("The password can't be null");
        }

        int passwordLength = user.getPassword().trim().length();
        if (passwordLength < VALID_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    String.format("The password must have at least %d symbols, but you have: %d",
                            VALID_PASSWORD_LENGTH, passwordLength));
        }
    }

    public void checkAgeAllowed(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("The age value can't be null");
        }

        if (user.getAge() < VALID_AGE) {
            throw new RegistrationException(
                    String.format("Your age must be at least %d, but you are: %d",
                            VALID_AGE, user.getAge()));
        }
    }
}
