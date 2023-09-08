package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.registrationexception.RegistrationException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH_OF_CREDENTIAL_VALUE = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserExist(user);
        checkLoginValidation(user);
        checkPasswordValidation(user);
        checkAgeValidation(user);
        return storageDao.add(user);
    }

    private void checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User has already existed with login "
                    + user.getLogin());
        }
    }

    private void checkLoginValidation(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MINIMAL_LENGTH_OF_CREDENTIAL_VALUE) {
            throw new RegistrationException("Login must have at least length "
                    + MINIMAL_LENGTH_OF_CREDENTIAL_VALUE);
        }
    }

    private void checkPasswordValidation(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH_OF_CREDENTIAL_VALUE) {
            throw new RegistrationException("Password must have at least length "
                    + MINIMAL_LENGTH_OF_CREDENTIAL_VALUE);
        }

    }

    private void checkAgeValidation(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Your age can't be null");
        }
        if (user.getAge() < VALID_AGE) {
            throw new RegistrationException("Age must have at least length " + VALID_AGE);
        }
    }
}
