package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CreateUserException;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CreateUserException("Can't create user");

        }
        if (isLoginValid(user) && isPasswordValid(user) && isAgeValid(user) && canAddUser(user)) {
            storageDao.add(user);
            return user;
        }
        throw new WrongDataException();
    }

    private boolean isLoginValid(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            return false;
        }
        return true;
    }

    private boolean isAgeValid(User user) {
        if (user.getAge() == null || user.getAge() < 18) {
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(User user) {
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return false;
        }
        return true;
    }

    private boolean canAddUser(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            return true;
        }
        throw new WrongDataException();
    }
}
