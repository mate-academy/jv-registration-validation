package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_AND_PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfValidData(user);
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private void checkIfValidData(User user) {
        checkNull(user);
        checkData(user);
    }

    private void checkNull(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        } else if (user.getLogin() == null) {
            throw new InvalidDataException("User login can't be null");
        } else if (user.getPassword() == null) {
            throw new InvalidDataException("User password can't be null");
        } else if (user.getAge() == null) {
            throw new InvalidDataException("User age can't be null");
        }
    }

    private void checkData(User user) {
        if (user.getLogin().length() < LOGIN_AND_PASSWORD_MIN_LENGTH) {
            throw new InvalidDataException("User login must have at least 6 characters");
        } else if (user.getPassword().length() < LOGIN_AND_PASSWORD_MIN_LENGTH) {
            throw new InvalidDataException("User password must have at least 6 characters");
        } else if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User must be at least" + MIN_AGE + "years old");
        }
    }
}

