package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataOfUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserIsNull(user);
        checkLoginIsCorrect(user);
        checkPasswordIsCorrect(user);
        checkUserAgeIsCorrect(user);
        checkLoginExist(user);
        storageDao.add(user);
        return user;
    }

    private boolean checkIfUserIsNull(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new InvalidDataOfUserException("can`t add null user");
        }
        return true;
    }

    private boolean checkLoginExist(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            return true;
        }
        throw new InvalidDataOfUserException("User with this login already exists");
    }

    private boolean checkLoginIsCorrect(User user) {
        if (user.getLogin().length() < 6) {
            throw new InvalidDataOfUserException("Login must be at least 6 characters long");
        }
        return true;
    }

    private boolean checkPasswordIsCorrect(User user) {
        if (user.getPassword().length() < 6) {
            throw new InvalidDataOfUserException("Password must be at least 6 character long");
        }
        return true;
    }

    private boolean checkUserAgeIsCorrect(User user) {
        if (user.getAge() < 18) {
            throw new InvalidDataOfUserException("User must be at least 18 years old ");
        }
        return true;
    }
}
