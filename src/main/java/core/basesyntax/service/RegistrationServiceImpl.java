package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidUserException {
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private static void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidUserException("Your password is NULL");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("Your password length is above "
                    + MIN_PASSWORD_LENGTH + " elements! Write a new one");
        }
    }

    private static void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidUserException("The age is null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("The age: " + user.getAge()
                    + " is below minimum value!");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserException("Current user doesn't exist! This is NULL value e-mail");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("Current user with login: " + user.getLogin()
                    + " already exists! Create new user with different e-mail");
        }
    }
}
