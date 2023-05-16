package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_AND_LOGIN_LENGTH = 6;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        validUser(user);
        return checkedUserForRegistration(user);
    }

    private User validUser(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        return user;
    }

    private User checkedUserForRegistration(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getLogin().trim().length() >= MIN_PASSWORD_AND_LOGIN_LENGTH
                    && user.getPassword().trim().length() >= MIN_PASSWORD_AND_LOGIN_LENGTH
                    && user.getAge() >= MIN_AGE) {
                return storageDao.add(user);
            }
        }
        return null;
    }
}
