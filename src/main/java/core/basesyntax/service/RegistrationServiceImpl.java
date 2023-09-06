package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.UserAlreadyExistsException;
import core.basesyntax.service.exception.UserIsNullException;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("Null-user can't be registered.");
        }
        if (userExists(user.getLogin())) {
            throw new UserAlreadyExistsException("User with login ["
                    + user.getLogin() + "] already exists");
        }
        if (loginIsValid(user.getLogin())
                && passwordIsValid(user.getPassword())
                && ageIsValid(user.getAge())) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    public boolean userExists(String login) {
        if (login == null) {
            return false;
        }
        if (storageDao.get(login) != null) {
            return true;
        }
        return false;
    }

    public boolean loginIsValid(String login) {
        if (login == null) {
            return false;
        }
        return login.length() >= 6 ? true : false;
    }

    public boolean passwordIsValid(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 6 ? true : false;
    }

    public boolean ageIsValid(Integer age) {
        if (age == null) {
            return false;
        }
        return age >= 18 ? true : false;
    }
}
