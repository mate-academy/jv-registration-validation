package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserDataNull(user);
        verifyCorrectCredentials(user);
        verifyAdultUser(user);
        verifyLoginNotUsed(user);
        return storageDao.add(user);
    }

    private void checkUserDataNull(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("No login found");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("No password found");
        }
    }

    private void verifyCorrectCredentials(User user) {
        if (user.getLogin().length() < MINIMAL_LENGTH) {
            throw new ValidationException(String.format("Login length must "
                    + "be more than %d",MINIMAL_LENGTH));
        }
        if (user.getPassword().length() < MINIMAL_LENGTH) {
            throw new ValidationException(String.format("Password length must "
                    + "be more than %d",MINIMAL_LENGTH));
        }
    }

    private void verifyLoginNotUsed(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with this login name already exist");
        }
    }

    private void verifyAdultUser(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new ValidationException(String.format("Age must be equal to or "
                    + "greater than %d", MINIMAL_AGE));
        }
    }
}
