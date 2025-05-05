package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SIZE_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user with such login already exists. "
                    + "Please, enter another login.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The user should be at least "
                    + MIN_AGE + " years old.");
        }
        if (user.getPassword().length() < MIN_SIZE_OF_PASSWORD) {
            throw new RuntimeException("Password should be at least "
                    + MIN_SIZE_OF_PASSWORD + " characters.");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user with such login already exists. "
                    + "Please, enter another login.");
        }
        return storageDao.add(user);

    }
}
