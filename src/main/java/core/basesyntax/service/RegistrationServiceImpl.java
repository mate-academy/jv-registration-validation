package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE_ALLOWED = 18;
    private static final int MINIMUM_ALLOWED_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Error! User can't be null.");
        }

        if (user.getLogin() == null) {
            throw new RuntimeException("Error! Login can't be empty");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Error! Password can't be empty");
        }

        if (user.getAge() == null) {
            throw new RuntimeException("Error! Age can't be empty");
        }

        if (user.getAge() < MINIMUM_AGE_ALLOWED) {
            throw new RuntimeException("Error! Below the age limit. Age must minimum "
                    + MINIMUM_AGE_ALLOWED);
        }

        if (user.getPassword().length() < MINIMUM_ALLOWED_PASSWORD_LENGTH) {
            throw new RuntimeException("Error! Password length must not be less than "
                    + MINIMUM_ALLOWED_PASSWORD_LENGTH);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error! The login entered belongs to another user account");
        }
        return storageDao.add(user);
    }
}
