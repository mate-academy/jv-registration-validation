package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LOGIN_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE_REGISTRATION = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User cant be null!!!");
        }

        loginValidation(user);
        passwordValidation(user);
        ageValidation(user);

        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User is already exist");
        }
        storageDao.add(user);
        return user;
    }

    public User getUserByLogin(String login) {
        User findedUser = storageDao.get(login);
        if (findedUser == null) {
            throw new ValidationException("User is not register yet");
        }
        return findedUser;
    }

    private void loginValidation(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("User login can not be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new ValidationException("User login can not be empty");
        }
        if (user.getLogin().length() < DEFAULT_LOGIN_PASSWORD_LENGTH) {
            throw new ValidationException("User login length cant be less "
                    + DEFAULT_LOGIN_PASSWORD_LENGTH + " characters");
        }
        if (Character.isDigit(user.getLogin().charAt(0))) {
            throw new ValidationException("User login cant start with number!");
        }
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("User password can not be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new ValidationException("User password can not be empty");
        }
        if (user.getPassword().length() < DEFAULT_LOGIN_PASSWORD_LENGTH) {
            throw new ValidationException("User password length cant be less "
                    + DEFAULT_LOGIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void ageValidation(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("User age can not be null");
        }
        if (user.getAge() < MINIMUM_AGE_REGISTRATION) {
            throw new ValidationException("User age cant be less " + MINIMUM_AGE_REGISTRATION);
        }
    }
}
