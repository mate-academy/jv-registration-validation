package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE_VALUE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (!checkUserLoginIsCorrect(user)) {
            throw new RegistrationException("User login is incorrect");
        }
        if (!checkUserPasswordIsCorrect(user)) {
            throw new RegistrationException("User password is incorrect");
        }
        if (!checkUserAgeIsCorrect(user)) {
            throw new RegistrationException("User age is incorrect");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User has already registered with this user name");
        }
        storageDao.add(user);
        return user;
    }

    private boolean checkUserLoginIsCorrect(User user) {
        return user.getLogin() != null
                && user.getLogin()
                .length() >= MIN_LOGIN_LENGTH;
    }

    private boolean checkUserPasswordIsCorrect(User user) {
        return user.getPassword() != null
                && user.getPassword()
                .length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean checkUserAgeIsCorrect(User user) {
        return user.getAge() != null
                && user.getAge() >= MIN_AGE_VALUE;
    }
}
