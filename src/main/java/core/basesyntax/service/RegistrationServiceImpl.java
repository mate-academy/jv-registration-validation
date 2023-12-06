package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    private boolean checkUserNull(User user) {
        if (user.getPassword() == null
                || user.getAge() == null
                || user.getLogin() == null) {
            throw new InvalidDataException("User's data is not valid");
        }
        return true;
    }

    private boolean loginAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Login already exist");
        }
        return true;
    }

    private boolean checkLogin(User user) {
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("such login is too short");
        }
        return true;
    }

    private boolean checkPassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("User password can not be null "
                    + "or shorter than 6");
        }
        return true;
    }

    private boolean checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User can not be younger than 18");
        }
        return true;
    }

    @Override
    public User register(User user) {
        checkUserNull(user);
        loginAlreadyExist(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }
}
