package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();
    //there is no user with such login in the Storage yet
    //user's login is at least 6 characters
    //user's password is at least 6 characters
    //user's age is at least 18 years old

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidUserException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User already exists");
        }
        if (user.getLogin().length() >= MIN_LENGTH_LOGIN
                && user.getPassword().length() >= MIN_LENGTH_PASSWORD
                && user.getAge() >= MIN_AGE) {
            storageDao.add(user);
        } else {
            throw new InvalidUserException("Incorrect data");
        }
        return user;
    }
}
