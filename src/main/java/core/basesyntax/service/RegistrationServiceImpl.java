package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already taken");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Sorry! You are younger than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("Password contains less than " + MIN_LENGTH_OF_PASSWORD);
        }
        checkIsNull(user);
        return storageDao.add(user);
    }

    private void checkIsNull(User user) {
        if (user == null || user.getAge() == null
                || user.getLogin() == null || user.getPassword() == null) {
            throw new NullPointerException("Information isn`t complete");
        }
    }
}
