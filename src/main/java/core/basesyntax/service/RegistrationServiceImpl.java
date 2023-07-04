package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("Login is too short, "
                    + "it must be more than 6 characters");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password is too short, "
                    + "it must be more than 6 characters");
        }

        if (user.getAge() == null
                || user.getAge() < MIN_USER_AGE) {
            throw new ValidationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_USER_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with this login exists try again");
        }
        return storageDao.add(user);
    }
}
