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
            throw new RuntimeException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getId() == null) {
            throw new RuntimeException("Id can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Minimal age for user is 18 years");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be longer then 5 characters");
        }
        if (user.getLogin().length() == 0) {
            throw new RuntimeException("Login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login has already existed");
        }
        return storageDao.add(user);
    }
}
