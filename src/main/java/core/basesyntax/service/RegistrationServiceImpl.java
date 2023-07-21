package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullUser(user);
        checkFieldsForNullValue(user);
        checkFieldsForMinValues(user);
        checkUserExistInStorage(user);
        return storageDao.add(user);
    }

    private void checkNullUser(User user) {
        if (user == null) {
            throw new RegistrationException("User shouldn't be null.");
        }
    }

    private void checkFieldsForNullValue(User user) {
        if (user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RegistrationException("All fields should be filled.");
        }
    }

    private void checkFieldsForMinValues(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("The login should be at least 6 characters.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("The password should be at least 6 characters.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("The age should be more than 18yo.");
        }
    }

    private void checkUserExistInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists with login " + user.getLogin());
        }
    }
}
