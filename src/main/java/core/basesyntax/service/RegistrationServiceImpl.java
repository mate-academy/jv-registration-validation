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
        if (user == null || user.getLogin() == null || user.getAge() == 0
                || user.getPassword() == null) {
            throw new RuntimeException("The user or one of the fields "
                    + "can't be null");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The user must be at least "
                    + "18 years old!");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("The user must have a password "
                    + "of at least 6 characters");
        } else if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("A user with this login already exists");
        } else if (user.getLogin().length() < 1) {
            throw new RuntimeException("Username can't be empty");
        }
        storageDao.add(user);
        return user;
    }
}
