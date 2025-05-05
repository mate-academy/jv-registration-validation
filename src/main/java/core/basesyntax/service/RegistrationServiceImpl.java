package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login has to be unique");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should be at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("Age can't be less then 18");
        }
        if (user.getId() == null) {
            throw new RuntimeException("ID can't be null");
        }
        return storageDao.add(user);
    }
}
