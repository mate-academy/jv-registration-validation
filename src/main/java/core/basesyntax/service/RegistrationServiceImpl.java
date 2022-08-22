package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNull(user);
        checkLoginNull(user);
        checkPasswordNull(user);
        checkAgeNull(user);
        if (checkUser(user)) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean checkUser(User user) {
        if ((storageDao.get(user.getLogin()) == null)
                && (user.getAge() >= MIN_AGE)
                && (user.getPassword().length() >= MIN_PASSWORD_LENGTH)) {
            return true;
        }
        throw new RuntimeException("Invalid data, check some fields");
    }

    private void checkUserNull(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid data, user is null");
        }
    }

    private void checkLoginNull(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid data, login is null");
        }
    }

    private void checkPasswordNull(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid data, password is null");
        }
    }

    private void checkAgeNull(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Invalid data, age is null");
        }
    }
}
