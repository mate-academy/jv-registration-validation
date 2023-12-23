package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationServiceImplException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_AND_PASSWORD_LEN = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
            checkUser(user);
            checkLogin(user);
            checkPassword(user);
            checkAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkUser(User user) throws RegistrationServiceImplException {
        if (user == null) {
            throw new RegistrationServiceImplException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceImplException("User with this login already exists");
        }
    }

    private void checkLogin(User user) throws RegistrationServiceImplException {
        if (user.getLogin() == null) {
            throw new RegistrationServiceImplException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_AND_PASSWORD_LEN) {
            throw new RegistrationServiceImplException(
                    "Login should consist of at least 6 characters");
        }
    }

    private void checkPassword(User user) throws RegistrationServiceImplException {
        if (user.getPassword() == null) {
            throw new RegistrationServiceImplException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LOGIN_AND_PASSWORD_LEN) {
            throw new RegistrationServiceImplException(
                    "Password should consist of at least 6 characters");
        }
    }

    private void checkAge(User user) throws RegistrationServiceImplException {
        if (user.getAge() == null) {
            throw new RegistrationServiceImplException("Age can't be null");
        }
        if (user.getAge() < 0) {
            throw new RegistrationServiceImplException("Age can't be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceImplException("Age must be over 18");
        }
    }
}
