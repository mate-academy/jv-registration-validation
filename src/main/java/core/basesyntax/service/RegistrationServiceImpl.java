package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_REQUIRED_AGE = 18;
    private static final int MIN_REQUIRED_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Нou have not entered all the required information");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user is already in the storage");
        }
        if (user.getAge() < MIN_REQUIRED_AGE) {
            throw new RuntimeException("User must be at least" + MIN_REQUIRED_AGE + "years old");
        }
        if (user.getPassword().length() < MIN_REQUIRED_PASSWORD_LENGTH) {
            throw new RuntimeException("Password min length must be "
                    + MIN_REQUIRED_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
