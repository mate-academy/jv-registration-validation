package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MINIMAL_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private static final int LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isUserLoginAlreadyExist(user.getLogin())) {
            throw new RegistrationException("User with this login already exist");
        }
        if (user.getAge() == null
                || isMinimalUserAgeNotMet(user.getAge())) {
            throw new RegistrationException("Minimal age requirement isn't met");
        }
        if (user.getLogin() == null
                || isStringLengthNotMet(user.getLogin(), LOGIN_LENGTH)) {
            throw new RegistrationException("Minimal login length requirement isn't met");
        }
        if (user.getPassword() == null
                || isStringLengthNotMet(user.getPassword(), PASSWORD_LENGTH)) {
            throw new RegistrationException("Minimal password length requirement isn't met");
        }
        return storageDao.add(user);
    }

    private boolean isUserLoginAlreadyExist(String login) {
        return storageDao.get(login) != null;
    }

    private boolean isMinimalUserAgeNotMet(int age) {
        return age < USER_MINIMAL_AGE;
    }

    private boolean isStringLengthNotMet(String stringToValidate, int requiredLength) {
        return stringToValidate.length() < requiredLength;
    }
}
