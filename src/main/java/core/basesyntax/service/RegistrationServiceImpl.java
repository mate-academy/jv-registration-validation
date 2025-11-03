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
        if (user.getAge() < MIN_AGE) {
            throw new NotValidUserException("User should be over 18");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new NotValidUserException("Login should be at least 6 characters long");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new NotValidUserException("Login should be at least 6 characters long");
        }
        return storageDao.add(user);
    }
}
