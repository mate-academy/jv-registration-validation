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
            throw new NotValidUserException("User can not be null");
        }
        if (user.getLogin() == null) {
            throw new NotValidUserException("Login should not be null");
        }
        User existing = storageDao.get(user.getLogin());
        if (existing != null) {
            throw new NotValidUserException("User is already registered");
        }
        if (user.getAge() == null) {
            throw new NotValidUserException("User age should not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotValidUserException("Not a valid age: "
                    + user.getAge()
                    + ". Min allowed age is "
                    + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new NotValidUserException("Login should be at least 6 characters long");
        }
        if (user.getPassword() == null) {
            throw new NotValidUserException("Password should not be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new NotValidUserException("Password should be at least 6 characters long");
        }
        return storageDao.add(user);
    }
}
