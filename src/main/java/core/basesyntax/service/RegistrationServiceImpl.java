package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if ((user.getLogin() == null)) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age can't be less than 18");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be less than 6 characters");
        }
        for (User userInStorage : Storage.people) {
            if (userInStorage.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User with such login is already exists");
            }
        }
        return storageDao.add(user);
    }
}
