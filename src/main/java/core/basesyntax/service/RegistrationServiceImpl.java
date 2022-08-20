package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 99;
    private static final int MIN_PSSWD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User should not be null");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("User's age should not be null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login should not be null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password should not be null");
        }
        for (User userLogin : Storage.people) {
            if (userLogin.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User should have unique login");
            }
        }
        if (user.getAge() < MIN_AGE && user.getAge() >= 0) {
            throw new RuntimeException("User too young!");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("User too old!");
        }
        if (user.getAge() < 0) {
            throw new RuntimeException("User's age cannot be negative!");
        }
        if (user.getPassword().length() < MIN_PSSWD_LENGTH) {
            throw new RuntimeException("Password too short!");
        }
        if (user.getLogin().length() == 0) {
            throw new RuntimeException("Login should not be blank!");
        }
        return storageDao.add(user);
    }
}
