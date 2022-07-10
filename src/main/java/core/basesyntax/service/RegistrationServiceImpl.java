package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Please, insert correct login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The storage already contains this user");
        }
        if (user.getAge() == null || user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("The age should be more than 18");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Incorrect password");
        }
        storageDao.add(user);
        return user;
    }
}
