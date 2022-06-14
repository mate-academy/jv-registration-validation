package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        passwordCheck(user);
        loginExistsCheck(user);
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RuntimeException("Age must be more than 17");
        }
    }

    private void passwordCheck(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("Password must be more than 5 symbols");
        }
    }

    private void loginExistsCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User login already exist");
        }
    }
}
