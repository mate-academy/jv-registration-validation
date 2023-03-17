package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("The user's login can`t  be null");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("The user's password can`t be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("The length of password cannot be less 6 characters!");
        }

        if (user.getAge() != null && user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("The user's age must be over 18!");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("The user's age  can`t be null!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with this login already exists");
        }

        return storageDao.add(user);
    }
}
