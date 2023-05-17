package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginValidator(user.getLogin());
        passwordValidator(user.getPassword());
        ageValidator(user.getAge());
        return storageDao.add(user);
    }

    private void loginValidator(String login) {
        if (login == null) {
            throw new ValidationException("Login can`t be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("Login too short, required length at least 6");
        }
        if (storageDao.get(login) != null) {
            throw new ValidationException("User with such login is already exist");
        }
    }

    private void passwordValidator(String password) {
        if (password == null) {
            throw new ValidationException("Password can`t be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password " + password + " too short"
                    + "required length at least 6");
        }
    }

    private void ageValidator(Integer age) {
        if (age == null) {
            throw new ValidationException("Age can`t be null");
        }
        if (age < MIN_AGE) {
            throw new ValidationException("User with age " + age + " too young,"
                    + "min allowed age is 18");
        }
    }
}
