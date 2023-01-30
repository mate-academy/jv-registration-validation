package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The user's age must be at least " + MIN_AGE + ".");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("The user's password must be at least " + MIN_PASSWORD_LENGTH
                    + " characters.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("The login must be specified.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already registered.");
        }
        return storageDao.add(user);
    }
}
