package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    private int passwordLength = 6;

    @Override
    public User register(User user) {
        checksUserForNull(user);
        checksIfAgeTooYoung(user);
        checksPasswordLength(user);
        checksValidLogin(user);
        checksUserNullFields(user);
        checkcIfAgeTooOld(user);
        return storageDao.add(user);
    }

    private boolean checksUserForNull(User user) {
        if (user == null) {
            throw new RuntimeException("user can't be null");
        }
        return true;
    }

    private boolean checksIfAgeTooYoung(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Sorry age must be above 18 and below 100");
        }
        return true;
    }

    private boolean checkcIfAgeTooOld(User user) {
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Sorry age must be above 18 and below 100");
        }
        return true;
    }

    private boolean checksPasswordLength(User user) {
        if (user.getPassword().length() < passwordLength) {
            throw new RuntimeException("Password is too short");
        }
        return true;
    }

    private boolean checksUserNullFields(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("User hasn't got any information");
        }
        return true;
    }

    private boolean checksValidLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Sorry such login already exists");
        }
        return true;
    }

}

