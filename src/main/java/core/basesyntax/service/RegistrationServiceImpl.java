package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_REGISTRATION_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNewUserData(user);
        return storageDao.add(user);
    }

    private void checkNewUserData(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can`t be null");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can`t be null");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("User`s age can`t be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationException("Login length is less than " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password length is less than "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_REGISTRATION_AGE) {
            throw new UserRegistrationException("User`s age is less than " + MIN_REGISTRATION_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User with this login already exist");
        }
    }
}
