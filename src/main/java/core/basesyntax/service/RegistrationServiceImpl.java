package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_USER_AGE = 18;
    private static final Integer MAX_USER_AGE = 100;
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        checkIfUserExist(user);
        storageDao.add(user);
        return user;
    }

    private void checkForNulls(User user) {
        if (user == null) {
            throw new IncorrectUserDataException("User is null");
        }
        if (user.getLogin() == null) {
            throw new IncorrectUserDataException("User login is null");
        }
        if (user.getAge() == null) {
            throw new IncorrectUserDataException("User age is null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().equals("")) {
            throw new IncorrectUserDataException("Login is empty");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() > MAX_USER_AGE) {
            throw new IncorrectUserDataException("User age is not valid");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new IncorrectUserDataException("User age is less than eighteen");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new IncorrectUserDataException("User password is not valid");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IncorrectUserDataException("User password is less than 6 symbols");
        }
    }

    private void checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new IncorrectUserDataException("User already exist");
        }
    }
}
