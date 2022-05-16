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
        if (user == null) {
            throw new RuntimeException("User could not be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login could not be null");
        }
        if (user.getLogin().trim().isEmpty()) {
            throw new RuntimeException("Login could not be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exists " + user.getLogin());
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age could not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age should be at least " + MIN_AGE + " years old");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Password could not be null");
        }
        if (user.getPassword().trim().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should be at least " + MIN_PASSWORD_LENGTH + " symbols");
        }
        storageDao.add(user);
        return user;
    }
}

