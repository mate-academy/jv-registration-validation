package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User storagedUser = storageDao.get(user.getLogin());
        if (user == null) {
            throw new RuntimeException("User could not be null.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login could not be null.");
        }
        if (storagedUser != null && user.getLogin().equals(storagedUser.getLogin())) {
            throw new RuntimeException("User with this login already exists.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password could not be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password could not be less than 6 symbols.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age could not be null.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User age could not be less than 18 or be negative.");
        }

        return storageDao.add(user);
    }
}
