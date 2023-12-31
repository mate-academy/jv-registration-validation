package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ACCEPTED_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static RegistrationException exception = new RegistrationException();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException(exception.USER_NULL_MSG);
        }
        if (!verifyUserLoginIsOk(user)) {
            throw new RegistrationException(exception.INCORRECT_LOGIN_MSG);
        }
        if (!verifyUserPasswordIsOk(user)) {
            throw new RegistrationException(exception.INCORRECT_PASSWORD_MSG);
        }
        if (!verifyUserAgeIsOk(user)) {
            throw new RegistrationException(exception.INCORRECT_AGE_MSG);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(exception.ALREADY_RGSTR_USER_MSG);
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean verifyUserLoginIsOk(User user) {
        return user.getLogin() != null
            && user.getLogin().length() >= MIN_LOGIN_LENGTH;
    }

    private boolean verifyUserPasswordIsOk(User user) {
        return user.getPassword() != null
            && user.getPassword().length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean verifyUserAgeIsOk(User user) {
        return user.getAge() != null
            && user.getAge() >= MIN_ACCEPTED_AGE;
    }
}

