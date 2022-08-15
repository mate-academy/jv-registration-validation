package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_AGE_OF_USER = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null.");
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("The password should be longer than "
                    + MIN_LENGTH_OF_PASSWORD + "characters.");
        }
        if (user.getAge() < MIN_AGE_OF_USER) {
            throw new RuntimeException("The user must be at least 18 years old.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user was added earlier.");
        }

        return storageDao.add(user);
    }
}
