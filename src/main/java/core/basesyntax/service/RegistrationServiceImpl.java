package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Necessary fields must be initialized");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already taken, please choose another one");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You must be 18 or older to register");
        } else if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must contain at least 6 characters");
        }
        storageDao.add(user);
        return user;
    }
}
