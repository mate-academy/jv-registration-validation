package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (!(user.getLogin() != null && storageDao.get(user.getLogin()) == null)) {
            throw new RuntimeException("The User is already in Storage");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The User must be at least " + MIN_AGE + " years old");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can`t be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("User password must be at least "
                    + MIN_LENGTH_OF_PASSWORD + " characters");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password can`t be null");
        }
        storageDao.add(user);
        return user;
    }
}
