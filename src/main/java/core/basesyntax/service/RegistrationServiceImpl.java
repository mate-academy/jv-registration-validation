package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        correctUserData(user);
        correctUserAge(user);
        correctLogin(user);
        correctPassword(user);
        return storageDao.add(user);
    }

    private void correctUserData(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Enter your date");
        }
    }

    private void correctUserAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Enter your age if you are over 18");
        }
    }

    private void correctLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such data already exists");
        }
    }

    private void correctPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Enter your password more than 6 characters");
        }
    }
}

