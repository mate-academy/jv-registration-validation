package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid login");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid password");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The age must be at least 18 but was " + user.getAge());
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Min pass length " + MIN_PASS_LENGTH
                    + ", but was " + user.getPassword().length());
        }
        return storageDao.add(user);
    }
}
