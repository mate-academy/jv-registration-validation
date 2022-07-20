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
            throw new RuntimeException("User can't be null.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login can't be null.");
        }
        if (storagedUser != null && user.getLogin().equals(storagedUser.getLogin())) {
            throw new RuntimeException("User with this login already exists.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be less than " + MIN_PASSWORD_LENGTH
                    + " symbols.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User age can't be less than " + MIN_USER_AGE
                    + " or be negative.");
        }
        return storageDao.add(user);
    }
}
