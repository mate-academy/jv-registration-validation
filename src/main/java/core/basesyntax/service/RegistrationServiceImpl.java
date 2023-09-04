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
        try {
            if (user == null) {
                throw new ValidationException("User can`t be null");
            }
            checkUserDataNull(user);
            checkLengthOfData(user);
            checkIsUserAdult(user);
            isLoginInStorage(user);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        storageDao.add(user);
        return user;
    }

    private User isLoginInStorage(User user) throws ValidationException {
        for (User userInStorage:Storage.PEOPLE) {
            if (userInStorage.getLogin().equals(user.getLogin())) {
                throw new ValidationException("User with this login name already exist");
            }
        }
        return user;
    }

    private User checkIsUserAdult(User user) throws ValidationException {
        if (user.getAge() < MINIMAL_AGE) {
            throw new ValidationException("Age must equals or more than "
                    + MINIMAL_AGE);
        }
        return user;
    }

    private User checkLengthOfData(User user) throws ValidationException {
        if (user.getLogin().length() < MINIMAL_LENGTH) {
            throw new ValidationException("Login length must be more than 6");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH) {
            throw new ValidationException("Password length must be more than 6");
        }
        return user;
    }

    private User checkUserDataNull(User user) throws ValidationException {
        if (user.getLogin() == null) {
            throw new ValidationException("No login found");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("No password found");
        }
        return user;
    }
}
