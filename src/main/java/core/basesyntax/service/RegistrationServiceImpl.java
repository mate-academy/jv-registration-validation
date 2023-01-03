package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNotFoundException("User is null and  can't register");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new UserNotFoundException("This login is incorrect");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserNotFoundException("This user is already registered");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() == 0) {
            throw new UserNotFoundException("This password is incorrect");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserNotFoundException("User password is less than expected");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new UserNotFoundException("User age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserNotFoundException("User age is less than allowed");
        }
    }

}
