package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ACCEPTABLE_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    + ". Min login length allowed is " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not valid password: " + user.getPassword()
                    + ". Min password length allowed is " + MIN_PASSWORD_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_ACCEPTABLE_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_ACCEPTABLE_AGE);
        }
        return storageDao.add(user);
    }
}
