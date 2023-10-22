package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.EmptyFieldException;
import core.basesyntax.exception.IllegalAgeException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.exception.LoginExistingException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String EMPTY_FIELD_MESSAGE = "Login/password or age is empty. "
            + "Please, write correct input data to continue!";
    private static final String INVALID_LOGIN_MESSAGE = "Login %s is incorrect. "
            + "Your login cannot be less than 6 characters!";
    private static final String INVALID_PASSWORD_MESSAGE = "Password %s is incorrect. "
            + "Your password cannot be less than 6 characters!";
    private static final String INCORRECT_AGE_MESSAGE = "Age %d is incorrect. "
            + "Age cannot be less than 18 years old or over the %d!";
    private static final String LOGIN_EXISTS_MESSAGE = "Login %s is already in storage. "
            + "Please, write another login to continue!";
    private static final int MAX_PERMISSIBLE_AGE = 122;
    private static final int MIN_PERMISSIBLE_AGE = 18;
    private static final int MIN_CHARACTER_AMOUNT = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        storageDao.add(user);
        return user;
    }

    private void validateUserData(User user) {
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        Integer userAge = user.getAge();
        if (userLogin == null || userPassword == null || userAge == null) {
            throw new EmptyFieldException(EMPTY_FIELD_MESSAGE);
        }
        for (User duplicatedUsed : Storage.people) {
            if (duplicatedUsed.getLogin().equals(user.getLogin())) {
                throw new LoginExistingException(String
                        .format(LOGIN_EXISTS_MESSAGE, userLogin));
            }
        }
        if (userLogin.length() < MIN_CHARACTER_AMOUNT) {
            throw new InvalidLoginException(String
                    .format(INVALID_LOGIN_MESSAGE, userLogin));
        }
        if (userPassword.length() < MIN_CHARACTER_AMOUNT) {
            throw new InvalidPasswordException(String
                    .format(INVALID_PASSWORD_MESSAGE, userPassword));
        }
        if (userAge < MIN_PERMISSIBLE_AGE || userAge > MAX_PERMISSIBLE_AGE) {
            throw new IllegalAgeException(String
                    .format(INCORRECT_AGE_MESSAGE, userAge, MAX_PERMISSIBLE_AGE));
        }
    }
}
