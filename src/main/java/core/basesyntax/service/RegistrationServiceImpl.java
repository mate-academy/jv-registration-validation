package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() <= MIN_AGE) {
            throw new RuntimeException("Cannot register this user. Minimum age must be 18");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Cannot register this user. "
                    + "Minimum password length 6 characters");
        }
        if (Storage.people.contains(user)) {
            throw new RuntimeException("This user has already been created!");
        }
        return storageDao.add(user);
    }
}
