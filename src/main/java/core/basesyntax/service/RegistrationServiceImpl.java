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
        if (user.getLogin() == null) {
            throw new RuntimeException("User`s login can`t be null.");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("User`s login must contain at least one character.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User`s age must be more than " + MIN_USER_AGE);
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User`s age can`t be NULL.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must contain at least six characters.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can`t be NULL.");
        }
        if (user.getPassword().isEmpty()) {
            throw new RuntimeException("Enter you password please.");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User`s login {" + user.getLogin() + "} is already exist.");
        }
        storageDao.add(user);
        return user;
    }
}

