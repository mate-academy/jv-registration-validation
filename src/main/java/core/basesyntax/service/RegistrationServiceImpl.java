package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("User login can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("User age can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("Such user already exist");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationException("User login should be 6 or more symbols");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("User password should be 6 or more symbols");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("User should be 18 or elder");
        }
        storageDao.add(user);
        return user;
    }
}
