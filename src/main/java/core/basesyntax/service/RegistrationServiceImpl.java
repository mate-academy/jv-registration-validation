package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();
        if (isLoginInvalid(login)) {
            throw new RegistrationException("Login should be least "
                    + MIN_LOGIN_LENGTH + "characters");
        }
        if (isPasswordInvalid(password)) {
            throw new RegistrationException("Password should be least "
                    + MIN_PASSWORD_LENGTH + "characters");
        }
        if (isAgeInvalid(age)) {
            throw new RegistrationException("Age should be least " + MIN_USER_AGE);
        }
        if (isLoginExisted(login)) {
            throw new RegistrationException("Login " + login + " is already used");
        }
        storageDao.add(user);
        return storageDao.get(login);
    }

    private boolean isLoginInvalid(String login) {
        return login == null || login.length() < MIN_LOGIN_LENGTH;
    }

    private boolean isPasswordInvalid(String password) {
        return password == null || password.length() < MIN_PASSWORD_LENGTH;
    }

    private boolean isAgeInvalid(Integer age) {
        return age == null || age < MIN_USER_AGE;
    }

    private boolean isLoginExisted(String login) {
        return storageDao.get(login) != null;
    }
}
