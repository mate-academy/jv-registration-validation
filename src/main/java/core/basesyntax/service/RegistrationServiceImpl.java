package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            new RuntimeException("User do not exist");
        }
        if (user.getLogin().isEmpty() || user.getLogin() == null) {
            new RuntimeException("User login can not be empty");
        }
        if (user.getAge() < USER_MIN_AGE) {
            new RuntimeException("User must be older 18");
        }
        if (user.getPassword() != null && user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            new RuntimeException("User login is too short, password must be at least 6 characters");
        }
        if (Storage.people.size() != 0) {
            checkUserLogin(user);

        } else {
            storageDao.add(user);
        }
        return user;
    }

    private void checkUserLogin(User user) {
        for (User newUser : Storage.people) {
            if (newUser.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User with such login already register");
            }
        }
    }
}
