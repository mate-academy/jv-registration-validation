package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checksUserForNull(user);
        checksIfAgeTooYoung(user);
        checksPasswordLength(user);
        checksUserLoginAlreadyExists(user);
        checksUserNullFields(user);
        checksIfAgeTooOld(user);
        return storageDao.add(user);
    }

    private void checksUserForNull(User user) {
        if (user == null) {
            throw new RuntimeException("user can't be null");
        }
    }

    private void checksIfAgeTooYoung(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Sorry age must be above 18 and below 100");
        }
    }

    private void checksIfAgeTooOld(User user) {
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Sorry age must be above 18 and below 100");
        }
    }

    private void checksPasswordLength(User user) {
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Password is too short");
        }
    }

    private void checksUserNullFields(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("User hasn't got any information");
        }
    }

    private void checksUserLoginAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Sorry such login already exists");
        }
    }

}

