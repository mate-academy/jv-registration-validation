package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userPasswordCheck(user);
        userAgeCheck(user);
        User userFromBase = storageDao.get(user.getLogin());
        if (userFromBase == null) {
            return storageDao.add(user);
        }
        throw new RuntimeException("User is already in use");
    }

    public boolean userPasswordCheck(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("you entered an empty string");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Sorry, password to short minimal length is "
                    + MIN_PASSWORD_LENGTH);
        }
        return true;
    }

    public boolean userAgeCheck(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("you entered an empty string");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Sorry, \n"
                    + "your age must be at least required to register"
                    + MIN_AGE + " years old");
        }
        return true;
    }
}
