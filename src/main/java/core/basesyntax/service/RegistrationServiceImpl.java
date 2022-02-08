package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (dataIsNull(user)) {
            throw new RuntimeException("Invalid data! User and user's fields should be not null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login: " + user.getLogin() + " already exist");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password isn't safe. Please, add more character.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your age is below the legal limit.");
        }
        return storageDao.add(user);
    }

    private boolean dataIsNull(User user) {
        return user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null;
    }
}
