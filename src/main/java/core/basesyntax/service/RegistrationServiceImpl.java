package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String EMPTY_FIELD_MESSAGE = "Login/password or age is empty. "
            + "Please, write correct input data to continue!";
    private static final String INVALID_LOGIN_MESSAGE = "Login %s is incorrect. "
            + "Your login cannot be less than 6 characters!";
    private static final String INVALID_PASSWORD_MESSAGE = "Password %s is incorrect. "
            + "Your password cannot be less than 6 characters!";
    private static final String INCORRECT_AGE_MESSAGE = "Age %d is incorrect. "
            + "Age cannot be less than 18 years old!";
    private static final String LOGIN_EXISTS_MESSAGE = "Login %s is already in storage. "
            + "Please, write another login to continue!";
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
        if (user == null) {
            throw new InvalidDataException(EMPTY_FIELD_MESSAGE);
        }
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        Integer userAge = user.getAge();
        if (userLogin == null) {
            throw new InvalidDataException(EMPTY_FIELD_MESSAGE);
        }
        if (userPassword == null) {
            throw new InvalidDataException(EMPTY_FIELD_MESSAGE);
        }
        if (userAge == null) {
            throw new InvalidDataException(EMPTY_FIELD_MESSAGE);
        }
        if (storageDao.get(userLogin) != null) {
            throw new InvalidDataException(String
                    .format(LOGIN_EXISTS_MESSAGE, userLogin));
        }
        if (userLogin.length() < MIN_CHARACTER_AMOUNT) {
            throw new InvalidDataException(String
                    .format(INVALID_LOGIN_MESSAGE, userLogin));
        }
        if (userPassword.length() < MIN_CHARACTER_AMOUNT) {
            throw new InvalidDataException(String
                    .format(INVALID_PASSWORD_MESSAGE, userPassword));
        }
        if (userAge < MIN_PERMISSIBLE_AGE) {
            throw new InvalidDataException(String
                    .format(INCORRECT_AGE_MESSAGE, userAge));
        }
    }
}
