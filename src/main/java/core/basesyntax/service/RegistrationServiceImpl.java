package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new InvalidUserException("User data can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("This user is registered");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Not valid age");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("User password must be more than 6 characters");
        }
        return storageDao.add(user);
    }
}
