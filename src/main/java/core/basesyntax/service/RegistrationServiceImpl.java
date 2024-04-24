package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }

        if (user.getLogin() == null) {
            throw new UserRegistrationException("User`s login can`t be null");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationException("User`s login length must contains at least"
                    + MIN_LOGIN_LENGTH
                    + "characters");
        }

        if (user.getPassword() == null) {
            throw new UserRegistrationException("User`s password can`t be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("User`s password length must contains at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters");
        }

        if (user.getAge() == null) {
            throw new UserRegistrationException("User`s age can`t be null");
        }

        if (user.getAge() < MIN_USER_AGE) {
            throw new UserRegistrationException("User`s age must be at least "
                    + MIN_USER_AGE
                    + " years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User with this login already exist");
        }

        return storageDao.add(user);
    }
}
