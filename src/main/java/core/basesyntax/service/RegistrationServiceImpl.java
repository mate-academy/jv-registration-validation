package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_LOGIN = 6;
    static final int MIN_PASSWORD = 8;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() <= MIN_LOGIN) {
            throw new RegistrationException("Login error. Minimum login length must be greater than " + MIN_LOGIN);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() <= MIN_PASSWORD) {
            throw new RegistrationException("Login error. Minimum login length must be greater than " + MIN_PASSWORD);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() <= 0) {
            throw new RegistrationException("Not valid age: " + user.getAge() + ". Age can't be less than zero");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin() != null) {
            User existedUser = storageDao.get(user.getLogin());
            if (existedUser != null) {
                throw new RegistrationException("User login \"" + user.getLogin()
                        + "\" already exists");
            }
        }
        return storageDao.add(user);
    }
}
