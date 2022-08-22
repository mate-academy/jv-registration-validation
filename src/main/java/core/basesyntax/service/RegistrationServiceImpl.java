package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null || user.getLogin() == null || user.getAge() == null) {
            throw new NullPointerException("Null value");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user.getLogin() + " already exists");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be more than " + MIN_AGE + " years");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("This field can`t be empty");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length should be greater then "
                    + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
