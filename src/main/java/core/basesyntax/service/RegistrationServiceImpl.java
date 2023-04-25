package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SIZE_LOGIN = 6;
    private static final int MIN_SIZE_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationUserException("Login can't be null");
        }
        if (user == null) {
            throw new RegistrationUserException("User can't be null");
        }
        if (user.getLogin().length() < MIN_SIZE_LOGIN) {
            throw new RegistrationUserException("Login must be at least "
                    + MIN_SIZE_LOGIN + "characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationUserException("Password can't be null");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationUserException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_SIZE_PASSWORD) {
            throw new RegistrationUserException("Password must be at least : "
                    + MIN_SIZE_PASSWORD + "characters");
        }
        return storageDao.add(user);
    }
}
