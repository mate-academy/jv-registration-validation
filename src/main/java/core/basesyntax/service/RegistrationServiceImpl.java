package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_SIZE_OF_PASSWORD = 6;
    public static final int MAX_AGE = 123;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null!");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can't be null or empty!");
        }
        User findUser = storageDao.get(user.getLogin());
        if (findUser != null) {
            throw new RuntimeException("The user with such login already exists. "
                        + "Please, enter another login.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The user should be at least "
                        + MIN_AGE + " years old.");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("The user should be less "
                        + MAX_AGE + " years old.");
        }
        if (user.getPassword().length() < MIN_SIZE_OF_PASSWORD) {
            throw new RuntimeException("Password should be at least "
                        + MIN_SIZE_OF_PASSWORD + " characters.");
        }
        return storageDao.add(user);
    }
}


