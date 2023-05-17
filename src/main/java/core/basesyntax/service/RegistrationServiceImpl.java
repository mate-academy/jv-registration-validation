package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void loginValidation(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidInputException("Login must be at least 6 characters long");
        } else if (storageDao.get(login) != null) {
            throw new InvalidInputException("User with this login already exists");
        }
    }

    private void passwordValidation(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException("Password must be at least 6 characters long");
        }
    }

    private void ageValidation(int age) {
        if (age < 18) {
            throw new InvalidInputException("You must be at least 18 years old for register");
        }
    }
}
