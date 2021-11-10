package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ALLOWABLE_AGE = 18;
    private static final int MIN_ALLOWABLE_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (confirmData(user)) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean confirmData(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Invalid data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("A user with this login already exists");
        }
        if (user.getAge() < 0) {
            throw new RuntimeException("User age cannot be less than 0");
        }
        if (user.getAge() < MIN_ALLOWABLE_AGE) {
            throw new RuntimeException("The user is not 18 years old");
        }
        if (user.getPassword().length() < MIN_ALLOWABLE_PASSWORD_LENGTH) {
            throw new RuntimeException("user password must be at least 6 characters");
        }
        return true;
    }
}
