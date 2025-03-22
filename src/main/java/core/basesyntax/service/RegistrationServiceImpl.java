package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_STRING = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User must not be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age must not be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("The user must be at least "
                    + MIN_USER_AGE + " years old.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login must not be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("User login must not be empty!");
        }
        if (user.getLogin().length() < MIN_LENGTH_STRING) {
            throw new RegistrationException("The login length must be at least "
                    + MIN_LENGTH_STRING + " characters.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password must not be null!");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException("User password must not be empty!");
        }
        if (user.getPassword().length() < MIN_LENGTH_STRING) {
            throw new RegistrationException("The password length must be at least "
                    + MIN_LENGTH_STRING + " characters.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This user is already registered.");
        }
        return storageDao.add(user);
    }
}
