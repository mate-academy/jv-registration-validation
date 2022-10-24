package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User Can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null!");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Password less than 6 symbols!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age less than 18 years old!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exist!");
        }
        return storageDao.add(user);
    }
}
