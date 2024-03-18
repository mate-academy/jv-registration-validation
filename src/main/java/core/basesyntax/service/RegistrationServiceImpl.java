package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.UserInvalidExeption;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new UserInvalidExeption("User cant be null!");
        }
        validateUserLogin(user.getLogin());
        validateUserPassword(user.getPassword());
        validateUserAge(user.getAge());
    }

    private void validateUserLogin(String login) {
        if (login == null) {
            throw new UserInvalidExeption("Please enter the login!");
        }
        if (login.length() < MINIMAL_LENGTH) {
            throw new UserInvalidExeption("Login " + login + " must be at least "
                    + MINIMAL_LENGTH + " characters!");
        }
        if (storageDao.get(login) != null) {
            throw new UserInvalidExeption("Login " + login + " already taken!");
        }
    }

    private void validateUserPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new UserInvalidExeption("Please enter password!");
        }
        if (password.length() < MINIMAL_LENGTH) {
            throw new UserInvalidExeption("Password must be at least "
                    + MINIMAL_LENGTH + " characters!");
        }
    }

    private void validateUserAge(int age) {
        if (age == 0) {
            throw new UserInvalidExeption("Age cant be 0");
        }
        if (age < MINIMAL_AGE) {
            throw new UserInvalidExeption("Age need to be " + MINIMAL_AGE + " or over!");
        }
    }
}
