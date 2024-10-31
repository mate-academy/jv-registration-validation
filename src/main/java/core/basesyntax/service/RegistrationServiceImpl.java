package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN = 6;
    private static final int MIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN) {
            throw new UserValidationException("Login is too short!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("User with login: \""
                    + user.getLogin() + "\" already exists");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new UserValidationException("Password is too short!");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
