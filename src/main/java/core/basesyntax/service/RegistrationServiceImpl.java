package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_PERMISSIBLE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public boolean register(User user) {
        checkExceptions(user);
        storageDao.add(user);
        return true;
    }

    private void checkExceptions(User user) {
        if (user == null) {
            throw new RegistrationServiceException("Unable to register a non-existent user.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("A user must not be registered "
                    + "if the same user already exists.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login not specified.");
        }
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Age not specified.");
        } else if (user.getAge() < MIN_PERMISSIBLE_AGE) {
            throw new RegistrationServiceException("Invalid user age.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password not specified.");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationServiceException("Invalid user password.");
        }
    }
}
