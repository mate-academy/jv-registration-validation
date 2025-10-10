package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User should not be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login must not be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password must not be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Age must not be null!");
        }
        if (user.getLogin().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new RegistrationServiceException(
                    "Your login length should be at least 6 characters!"
            );
        }
        if (user.getPassword().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new RegistrationServiceException(
                    "Your password must not be less than 6 characters!"
            );
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException(
                    "Your login " + user.getLogin() + " already exists."
            );
        }
        if (user.getAge() < MIN_AGE && user.getAge() > 0) {
            throw new RegistrationServiceException(
                    "Your age is lower than 18."
            );
        }
        if (user.getAge() < 0) {
            throw new RegistrationServiceException(
                    "Your age must not be negative!"
            );
        }
        return storageDao.add(user);
    }
}
