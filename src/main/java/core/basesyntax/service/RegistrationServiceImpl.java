package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserDataNull(user);
        checkLengthOfData(user);
        checkIsUserAdult(user);
        isLoginInStorage(user);
        return storageDao.add(user);
    }

    private User isLoginInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with this login name already exist");
        }
        return user;
    }

    private User checkIsUserAdult(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new ValidationException("Age must equals or more than "
                    + MINIMAL_AGE);
        }
        return user;
    }

    private User checkLengthOfData(User user) {
        if (user.getLogin().length() < MINIMAL_LENGTH) {
            throw new ValidationException("Login length must be more than " + MINIMAL_LENGTH);
        }
        if (user.getPassword().length() < MINIMAL_LENGTH) {
            throw new ValidationException("Password length must be more than " + MINIMAL_LENGTH);
        }
        return user;
    }

    private User checkUserDataNull(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("No login found");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("No password found");
        }
        return user;
    }
}
