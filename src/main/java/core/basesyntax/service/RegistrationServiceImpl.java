package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User u : Storage.people) {
            if (user.getLogin() == u.getLogin()) {
                throw new RuntimeException("User with such login exist in the Storage");
            }
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getLogin().equals("")) {
            throw new RuntimeException("Login can't be empty. Please enter at "
                  + "least one character.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age must be over 18");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        storageDao.add(user);
        return user;
    }
}
