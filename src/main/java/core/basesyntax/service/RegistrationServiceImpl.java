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
            throw new UserValidationException("User must not be null");
        }

        boolean userAttributesNotNull = areUserAttributesNotNull(user);
        if (!userAttributesNotNull) {
            throw new UserValidationException("User's attributes must not be null");
        }

        User normalizedUser = normalizeUser(user);

        boolean loginIsValid = isLoginValid(normalizedUser.getLogin());
        if (!loginIsValid) {
            throw new UserValidationException("User's login must be at least 6 characters long");
        }

        boolean loginIsUnique = isLoginUnique(normalizedUser.getLogin());
        if (!loginIsUnique) {
            throw new UserValidationException("User with such login already exists");
        }

        boolean passwordIsValid = isPasswordValid(normalizedUser.getPassword());
        if (!passwordIsValid) {
            throw new UserValidationException("User's password must be at least 6 characters long");
        }

        boolean ageIsValid = isAgeValid(normalizedUser.getAge());
        if (!ageIsValid) {
            throw new UserValidationException("User's age must be at least 18");
        }

        return this.storageDao.add(normalizedUser);
    }

    private boolean areUserAttributesNotNull(User user) {
        return user.getLogin() != null && user.getPassword() != null && user.getAge() != null;
    }

    private boolean isLoginUnique(String login) {
        if (login == null) {
            throw new UserValidationException("User's login must not be null");
        }
        return this.storageDao.get(login) == null;
    }

    private boolean isLoginValid(String login) {
        return login != null && login.trim().length() >= MIN_LOGIN_LENGTH;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age >= MIN_AGE;
    }

    private User normalizeUser(User user) {
        boolean userAttributesNotNull = areUserAttributesNotNull(user);
        if (!userAttributesNotNull) {
            throw new UserValidationException("User's attributes must not be null");
        }

        User normalizedUser = new User();
        normalizedUser.setLogin(user.getLogin().trim());
        normalizedUser.setPassword(user.getPassword().trim());
        normalizedUser.setAge(user.getAge());
        normalizedUser.setId(user.getId());

        return normalizedUser;
    }
}
