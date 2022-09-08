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
        validateUser(user);
        storageDao.add(user);
        return null;
    }

    private void validateUser(User user) {
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("User age cannot be less than " + MINIMUM_AGE);
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("User password cannot be less than "
                    + MINIMUM_PASSWORD_LENGTH);
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("User login cannot be empty!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User login already exists. Create a new one please.");
        }
    }
}
