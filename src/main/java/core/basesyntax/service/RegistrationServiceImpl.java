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
        if (user == null) {
            throw new RuntimeException("User cant be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login cant be null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RuntimeException("Age cant be null or age is less than: " + MIN_AGE);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be null "
                    + "or password is less than: " + MIN_PASSWORD_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exist");
        }
        return storageDao.add(user);
    }
}


