package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() <= 0
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Incorrect data");
        }
        if (user.getAge() < MIN_AGE
                || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || userWithSameLoginExists(user)) {
            throw new RuntimeException("Incorrect data");
        }
        return storageDao.add(user);
    }

    private boolean userWithSameLoginExists(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
