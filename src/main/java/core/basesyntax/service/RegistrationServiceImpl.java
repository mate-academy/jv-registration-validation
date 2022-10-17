package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MIN_AGE_FOR_REGISTER = 18;
    private static final int MAX_AGE = 125;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        checkCorrectAge(user);
        checkCorrectPassword(user);
        return userAdd(user);
    }

    private User userAdd(User user) {
        User findUser = storageDao.get(user.getLogin());
        if (findUser != null) {
            throw new RuntimeException("User login is already taken");
        }
        storageDao.add(user);
        return user;
    }

    private void checkNull(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid user. User info can't be read");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid user login. Login can't be read");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Invalid user age. Age can't be read");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid user password. Password can't be read");
        }
    }

    private void checkCorrectPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_SIZE) {
            throw new RuntimeException("User password must be is at least 6 characters");
        }
    }

    private void checkCorrectAge(User user) {
        if (user.getAge() < MIN_AGE_FOR_REGISTER) {
            throw new RuntimeException("User age must be greater 18");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Invalid user age - " + user.getAge());
        }
    }
}
