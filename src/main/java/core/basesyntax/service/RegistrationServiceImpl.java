package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static int MIN_PASSWORD_LENGTH = 6;
    private static int MIN_AGE = 18;
    private StorageDao storageDao;

    public RegistrationServiceImpl() {
        this.storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new NullPointerException("Null input");
        }
        if (storageDao.get(user.getLogin()) == null
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && user.getAge() >= MIN_AGE) {
            storageDao.add(user);
        } else {
            throw new RuntimeException("Invalid user data");
        }
        return user;
    }
}
