package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_USER_AGE = 18;
    public static final int MIN_LOGIN_CHARACTERS = 6;
    public static final int MIN_PASSWORD_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User login can't be equal null");
        } else if (user.getLogin().length() < MIN_LOGIN_CHARACTERS) {
            throw new RegistrationException("Login length is "
                    + user.getLogin().length() + ", minimal length " + MIN_LOGIN_CHARACTERS);
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        } else if (user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationException("Password length is "
                    + user.getPassword().length() + ", minimal length " + MIN_PASSWORD_CHARACTERS);
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        } else if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("User age is "
                    + user.getAge() + ". Minimal allowed user age is " + MIN_USER_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login "
                    + user.getLogin() + " already exist");
        }
        return storageDao.add(user);
    }
}
