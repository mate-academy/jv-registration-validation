package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.NotValidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NotValidUserException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new NotValidUserException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new NotValidUserException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new NotValidUserException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new NotValidUserException("Length of the login can't be " + user.getLogin().length() + " it, must be at least " + MIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new NotValidUserException("Length of the password can't be "
                    + user.getPassword().length() + ", it must be at least " + MIN_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotValidUserException("Not valid age: "
                    + user.getAge() + ", it must be at least " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidUserException("User with this login: "
                    + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}

