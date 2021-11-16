package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        if (Storage.people.contains(user)) {
            throw new RuntimeException("User already exists!");
        }
        return storageDao.add(user);
    }

    private void validateUserData(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null
                || user.getLogin().equals("")
                || user.getAge() < MINIMUM_USER_AGE
                || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid user data!");
        }
    }
}
