package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MIN_LENGTH_LOGIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    private void validateUserData(User user) {
        validatePassword(user);
        validateLogin(user);
        validateAge(user);
        addExistUser(user);
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Not valid password: " + user.getPassword()
                    + ". Min length password is " + MIN_LENGTH_PASSWORD);
        } else if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidUserDataException("Password can't be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        } else if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new InvalidUserDataException("Not valid login: " + user.getLogin()
                    + ". Min length login is " + MIN_LENGTH_LOGIN);
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidUserDataException("Age can't be null");
        } else if (user.getAge() <= MIN_AGE) {
            throw new InvalidUserDataException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
    }

    private void addExistUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("A user with this login already exists");
        }
    }
}
