package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RegisterException(user.getLogin() + " is already registered");
        }
        if (user.getLogin() == null) {
            throw new RegisterException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegisterException("User's login must be at least 6 characters");
        }
        if (user.getPassword() == null) {
            throw new RegisterException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegisterException("User's password must be at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegisterException("Age can't be null");
        }
        if (user.getAge() < 0) {
            throw new RegisterException("Age can't be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        storageDao.add(user);
        return user;
    }
}
