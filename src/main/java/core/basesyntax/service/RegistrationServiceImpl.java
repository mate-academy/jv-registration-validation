package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Null input");
        }
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Invalid login");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Invalid age");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid password");
        }
        return storageDao.add(user);
    }
}
