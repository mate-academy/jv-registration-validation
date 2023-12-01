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
    public User register(User user) throws InvalidUserDataException {
        if (userValidator(user) && foundUserInDatabase(user)) {
            storageDao.add(user);
        }
        return user;
    }

    @Override
    public boolean foundUserInDatabase(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            return false;
        }
        return storageDao.get(user.getLogin()).getLogin().equals(user.getLogin());
    }

    @Override
    public boolean userValidator(User user) throws InvalidUserDataException {
        if (user == null) {
            throw new InvalidUserDataException("User can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Not valid age");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        if (user.getLogin().length() < MIN_FIELD_LENGTH) {
            throw new InvalidUserDataException("Login must contain more than six characters");
        }
        if (user.getPassword().length() < MIN_FIELD_LENGTH) {
            throw new InvalidUserDataException("Password must contain more than six characters");
        }
        return true;
    }
}
