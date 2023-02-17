package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_REQUIRED_AGE = 18;
    private static final int MIN_REQUIRED_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Please fill in the login field!");
        }

        if (user.getAge() == null || user.getAge() < MIN_REQUIRED_AGE) {
            throw new ValidationException("Users age must be 18 or greater!");
        }

        if (user.getPassword() == null
                || user.getPassword().length() < MIN_REQUIRED_PASSWORD_LENGTH) {
            throw new ValidationException("The password must contain minimum 6 characters!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("The user with this login is already exist!");
        }

        return storageDao.add(user);
    }
}
