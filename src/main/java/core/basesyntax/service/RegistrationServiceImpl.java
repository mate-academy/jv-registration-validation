package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 200;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Password not Ok!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() >= MAX_AGE) {
            throw new RuntimeException("Age not Ok!");
        }
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login not Ok!");
        }
        return storageDao.add(user);
    }
}
