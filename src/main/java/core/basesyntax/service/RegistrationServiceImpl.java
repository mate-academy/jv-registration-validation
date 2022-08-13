package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User doesn't have any initialized fields");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new RuntimeException("The login field can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user.getLogin() + " already exists");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must contain at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null || user.getAge() <= 0) {
            throw new RuntimeException("The age value should be positive");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User must be at least " + MIN_USER_AGE + " years old");
        }
        return storageDao.add(user);
    }
}
