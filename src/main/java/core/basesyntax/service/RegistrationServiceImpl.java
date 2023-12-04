package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AlreadyExistException;
import core.basesyntax.exception.InvalidValueException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_FOR_LOGIN_AND_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            if (storageDao.get(user.getLogin()) == null) {
                if (user.getLogin().length() >= MIN_LENGTH_FOR_LOGIN_AND_PASSWORD
                        && user.getPassword().length() >= MIN_LENGTH_FOR_LOGIN_AND_PASSWORD
                        && user.getAge() >= MIN_AGE) {
                    return storageDao.add(user);
                }
                throw new InvalidValueException("User " + user + "has invalid data.");
            }
            throw new AlreadyExistException("User " + user + "already exist.");
        }
        return null;
    }
}

