package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("User is null");
        }

        boolean loginIsUnique = isLoginUnique(user.getLogin());
        if (!loginIsUnique) {
            throw new UserValidationException("User with such login already exists");
        }

        boolean loginIsValid = isLoginValid(user.getLogin());
        if (!loginIsValid) {
            throw new UserValidationException("User's login must be at least 6 characters long");
        }

        boolean passwordIsValid = isPasswordValid(user.getPassword());
        if (!passwordIsValid) {
            throw new UserValidationException("User's password must be at least 6 characters long");
        }

        boolean ageIsValid = isAgeValid(user.getAge());
        if (!ageIsValid) {
            throw new UserValidationException("User's age must be at least 18");
        }

        return this.storageDao.add(user);
    }

    private boolean isLoginUnique(String login) {
        return this.storageDao.get(login) == null;
    }

    private boolean isLoginValid(String login) {
        return login != null && login.length() >= MIN_LOGIN_LENGTH;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age >= MIN_AGE;
    }
}
