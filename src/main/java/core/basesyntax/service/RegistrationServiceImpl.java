package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LOGIN_SIZE = 1;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("user should not be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("user's login should not be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("user's password should not be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("user's age should not be null");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_SIZE) {
            throw new InvalidDataException("user login have to be longer than "
                    + MINIMAL_LOGIN_SIZE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("there already is user with same login in storage");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new InvalidDataException("user age is under " + MINIMAL_AGE);
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new InvalidDataException("user password should be at least "
                    + MINIMAL_PASSWORD_LENGTH + " characters");
        }
        storageDao.add(user);
        return user;
    }
}
