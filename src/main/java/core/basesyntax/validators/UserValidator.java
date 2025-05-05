package core.basesyntax.validators;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.RegistrationException;
import core.basesyntax.model.User;

public class UserValidator {
    private static final String INCORRECT_PASSWORD_PATTERN_MESSAGE =
            """
                    Password must contain at least one digit [0-9].
                    Password must contain at least one lowercase Latin character [a-z].
                    Password must contain at least one uppercase Latin character [A-Z].
                    Password must contain at least one special character like ! @ # & ( ).
                    Password must contain a length of at least 8 characters and a maximum\s
                    of 20 characters.""";
    private static final String NEGATIVE_AGE_MESSAGE =
            "Age could not be a negative number";
    private static final String PASSWORD_REGEXP =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with given login "
            + "is already exists";
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao;

    public UserValidator() {
        this.storageDao = new StorageDaoImpl();
    }
    
    public User validateUser(User user) throws RegistrationException {
        validateForNullableValues(user);
        validatePasswordStrength(user);
        validateAge(user);
        validateUserExistInStorage(user);
        return user;
    }

    private void validateForNullableValues(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getId() == null) {
            throw new RegistrationException("User id cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Age cannot be null");
        }
    }

    private void validatePasswordStrength(User user) throws RegistrationException {
        if (!user.getPassword().matches(PASSWORD_REGEXP)) {
            throw new RegistrationException(INCORRECT_PASSWORD_PATTERN_MESSAGE);
        }
    }

    private void validateAge(User user) throws RegistrationException {
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException(NEGATIVE_AGE_MESSAGE);
        }
    }

    private void validateUserExistInStorage(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(USER_ALREADY_EXISTS_MESSAGE);
        }
    }
}
