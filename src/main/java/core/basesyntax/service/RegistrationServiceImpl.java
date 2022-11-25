package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("User password cannot be shorter then 6 characters,"
                    + " but actual: " + user.getPassword());
        }
        if (user.getAge() == null || user.getAge() < MINIMUM_USER_AGE) {
            throw new RuntimeException("User cannot be younger then 18 age, but actual: "
                    + user.getAge());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is already a user with this login!");
        }
        return storageDao.add(user);
    }
}
