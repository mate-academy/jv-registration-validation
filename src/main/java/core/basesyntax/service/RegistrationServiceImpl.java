package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.userinvalidexeption.UserInvalidExeption;

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
        validateUserLogin(user.getLogin());
        validateUserPassword(user.getPassword());
        validateUserAge(user.getAge());
    }

    private void validateUserLogin(String login) {
        if (login == null) {
            throw new UserInvalidExeption("Please enter the login!");
        }
        if (login.length() < MINIMAL_LENGTH) {
            throw new UserInvalidExeption("Login " + login + " must be at least 6 characters!");
        }
        if (storageDao.get(login) != null) {
            throw new UserInvalidExeption("Login " + login + " already taking!");
        }
    }

    private void validateUserPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new UserInvalidExeption("Please enter password!");
        }
        if (password.length() < MINIMAL_LENGTH) {
            throw new UserInvalidExeption("Password must be at least 6 characters");
        }
    }

    private void validateUserAge(int age) {
        if (age <= MINIMAL_AGE) {
            throw new UserInvalidExeption("Only adults allowed!!!");
        }
    }
}
