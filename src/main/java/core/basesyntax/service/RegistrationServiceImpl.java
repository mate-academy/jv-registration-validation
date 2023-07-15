package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null!");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User's login is null!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("User's password is null!");
        }
        int loginsLength = user.getLogin().length();
        if (loginsLength < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("User's length of login is " + loginsLength
                    + ". It shouldn't be shorter than 6 characters!");
        }
        int passwordsLength = user.getPassword().length();
        if (passwordsLength < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("User's length of password is " + passwordsLength
                    + ". It shouldn't be shorter than 6 characters!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User is not valid!");
        }
        if (contains(user)) {
            throw new InvalidDataException("The storage already have user with login "
                    + user.getLogin());
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    private boolean contains(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
