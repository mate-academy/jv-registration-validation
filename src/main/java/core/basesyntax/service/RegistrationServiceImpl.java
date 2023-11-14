package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USERS_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        this.storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (validation(user)) {
            return storageDao.add(user);
        }
        return null;
    }

    private boolean validation(User user) {
        if (user == null) {
            throw new ValidationException("User can`t be null");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("User`s password length can`t be null or less than "
                    + MIN_PASSWORD_LENGTH);
        }

        if (user.getAge() == null || user.getAge() < MIN_USERS_AGE) {
            throw new ValidationException("User`s age can`t be null or less than " + MIN_USERS_AGE);
        }

        if (user.getLogin() == null) {
            throw new ValidationException("User`s login can`t be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with this login exist in storage");
        }
        return true;
    }
}
