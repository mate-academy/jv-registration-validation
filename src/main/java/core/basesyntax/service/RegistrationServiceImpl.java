package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_FIELD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
        if (user.getAge() == null) {
            throw new InvalidUserDataException("User age cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User age must be 18+");
        }
        if (user.getLogin().length() < MIN_FIELD_LENGTH) {
            throw new InvalidUserDataException("Login must be longer than six characters");
        }
        if (user.getPassword().length() < MIN_FIELD_LENGTH) {
            throw new InvalidUserDataException("Password must be longer than six characters");
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        }

        return storageDao.get(user.getLogin());
    }
}
