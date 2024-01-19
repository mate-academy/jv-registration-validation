package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int VALID_AGE = 18;
    private static final int VALID_LOGIN_LENGTH = 6;
    private static final int VALID_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getAge() < VALID_AGE) {
            throw new RegistrationException("User's age for registration " + user.getAge()
                    + " not allowed. Min age for registration is: " + VALID_AGE);
        }
        if (user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new RegistrationException("Login's length must be no less " + VALID_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RegistrationException("Password's length must be no less " + VALID_PASSWORD_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist");
        }
        storageDao.add(user);
        return user;
    }
}
