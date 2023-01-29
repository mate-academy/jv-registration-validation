package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginCheck(user);
        ageCheck(user);
        passwordCheck(user);
        storageDao.add(user);
        return user;
    }

    private void passwordCheck(User user) throws ValidationException {
        if (user.getPassword().length() < 6) {
            throw new ValidationException("Password is too short");
        }
    }

    private void ageCheck(User user) throws ValidationException {
        if (user.getAge() < 1) {
            throw new ValidationException("Impossible age");
        }
        if (user.getAge() < 18) {
            throw new ValidationException("User is too young to be registered");
        }
    }

    private void loginCheck(User user) throws ValidationException {
        if (user.getLogin() == null) {
            throw new ValidationException("Login can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with such login already exists");
        }
    }
}
