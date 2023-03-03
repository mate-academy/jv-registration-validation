package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Parameter User has a null value");
        }
        if (user.getAge() == null) {
            throw new IllegalArgumentException("Field Age has a null value");
        }
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Field Password has a null value");
        }
        if (user.getLogin() == null) {
            throw new IllegalArgumentException("Field Login has a null value");
        }
        if (user.getAge() < MIN_AGE) {
            throw new IllegalArgumentException("Age less than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new IllegalArgumentException("Password length less than " + MIN_LENGTH_PASSWORD);
        }
        return storageDao.get(user.getLogin()) == null ? storageDao.add(user) : null;
    }
}
