package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exaption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final byte MIN_PASSWORD_LENGTH = 6;
    private static final byte MIN_LOGIN_LENGTH = 6;
    private static final byte MIN_USER_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        existLoginValid(user);
        passwordLengthValid(user);
        loginLengthValid(user);
        ageValid(user);
        storageDao.add(user);
        return user;
    }

    private boolean existLoginValid(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is register");
        }
        return true;
    }

    private boolean passwordLengthValid(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User with this password is less than 6");
        }
        return true;
    }

    private boolean loginLengthValid(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User with this login is less than 6");
        }
        return true;
    }

    private boolean ageValid(User user) {
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("User age less than 18");
        }
        return true;
    }

    private boolean checkNull(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("User info can't be null");
        }
        return true;
    }
}
