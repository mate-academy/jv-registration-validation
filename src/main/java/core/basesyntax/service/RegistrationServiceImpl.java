package core.basesyntax.service;

import core.basesyntax.UserValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserValidationException {
        if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be empty.");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be empty.");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age can't be empty.");
        }
        if (userAlreadyExist(user.getLogin())) {
            throw new UserValidationException("User is already exist.");
        }
        if (!checkMinAge(user.getAge())) {
            throw new UserValidationException("User age " + user.getAge()
                    + " less then " + MIN_AGE + ".");
        }
        if (!checkLoginLength(user.getLogin())) {
            throw new UserValidationException("Login " + user.getLogin()
                    + " less than " + MIN_LOGIN_LENGTH + ".");
        }
        if (!checkPasswordLength(user.getPassword())) {
            throw new UserValidationException("Password less than " + MIN_PASSWORD_LENGTH + ".");
        }
        storageDao.add(user);
        return user;
    }

    private boolean userAlreadyExist(String login) {
        return storageDao.get(login) != null;
    }

    private boolean checkMinAge(Integer age) {
        return age >= MIN_AGE;
    }

    private boolean checkLoginLength(String login) {
        return login.length() >= MIN_LOGIN_LENGTH;
    }

    private boolean checkPasswordLength(String password) {
        return password.length() >= MIN_LOGIN_LENGTH;
    }
}
