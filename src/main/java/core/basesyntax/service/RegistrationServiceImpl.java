package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age can't be null or less than 18");
        }
        if (user.getLogin().isBlank() || user.getLogin() == null) {
            throw new RuntimeException("Login can't be null or empty");
        }
        if (user.getPassword().length() < MIN_LENGTH
                || user.getPassword().isBlank()
                || user.getPassword() == null) {
            throw new RuntimeException("Password can't be null or empty "
                    + "and less than 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }
        return storageDao.add(user);
    }
}
