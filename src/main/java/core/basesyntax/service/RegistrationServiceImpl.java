package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is already user with such login");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Provide login, please");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Provide age, please");
        }
        if (user.getAge() < 0) {
            throw new RuntimeException("User age must be positive number");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be 18 years old or older");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Provide password, please");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be at least 6 characters long");
        }
        storageDao.add(user);
        return user;
    }
}
