package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid password length. "
                    + "The minimum length is 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getLogin().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login length. "
                    + "The minimum length is 6 characters");
        }

        User checkUser = storageDao.get(user.getLogin());
        if (checkUser != null) {
            throw new RegistrationException("The user with login "
                    + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}
