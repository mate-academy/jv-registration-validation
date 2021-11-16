package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        validateUserData(user);
        checkIsLoginAlreadyRegistered(user.getLogin());
        return storageDao.add(user);
    }

    private void validateUserData(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null
                || user.getAge() < MINIMUM_USER_AGE
                || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid user data!");
        }
    }

    private void checkIsLoginAlreadyRegistered(String login) {
        for (User currentUser : Storage.people) {
            if (currentUser.getLogin().equals(login)) {
                throw new RuntimeException("User with this login already exists!");
            }
        }
    }
}
