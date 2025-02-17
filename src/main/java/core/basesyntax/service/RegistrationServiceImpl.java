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
        if (user == null) {
            throw new InvalidDataOfUserException("can`t add null user");
        }
        return true;
    }

    private boolean checkLoginExist(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            return true;
        }
        throw new InvalidDataOfUserException("is already exist");
    }

    private boolean checkLoginIsCorrect(User user) {
        if (user.getLogin().length() < 6) {
            throw new InvalidDataOfUserException(" login length is under 6");
        }
        return true;
    }

    private boolean checkPasswordIsCorrect(User user) {
        if (user.getPassword().length() < 6) {
            throw new InvalidDataOfUserException(" password length is under 6");
        }
        return true;
    }

    private boolean checkUserAgeIsCorrect(User user) {
        if (user.getAge() < 18) {
            throw new InvalidDataOfUserException(" age is under 18");
        }
        return true;
    }
}
