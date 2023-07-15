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
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new InvalidDataException("User contains null fields!");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH
                || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User is not valid!");
        }
        if (contains(user)) {
            throw new InvalidDataException("The storage already have user with login "
                    + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }

    private boolean contains(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
