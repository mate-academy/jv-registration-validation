package core.basesyntax.service;

import core.basesyntax.NoValidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LOGIN_LENGTH_MIN = 6;
    private static final int VALID_LOGIN_LENGTH_MAX = 20;
    private static final int VALID_PASSWORD_LENGTH_MIN = 6;
    private static final int VALID_PASSWORD_LENGTH_MAX = 20;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUsersData(user);
        return storageDao.add(user);
    }

    private void validateUsersData(User user) {
        checkIfUserIsNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        checkIfUserAlreadyExist(user);
    }

    private void checkIfUserIsNull(User user) {
        if (user == null) {
            throw new NoValidDataException("This user is null. "
                    + "Please set all fields for users");
        }
    }

    private void checkIfUserAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new NoValidDataException("User with this login already exist");
        }
    }

    private void validateLogin(User user) {
        checkIfFieldIsNull(user.getLogin(), "You can't create account without login."
                + " Please, set your login");
        checkCharactersInUsersLogin(user);
        checkIfUserHasInvalidLoginLength(user);
    }

    private void validatePassword(User user) {
        checkIfFieldIsNull(user.getPassword(),"You can't create account without password."
                + " Please, set your password");
        checkCharactersInUsersPassword(user);
        checkIfUserHasInvalidPasswordLength(user);
    }

    private void validateAge(User user) {
        checkIfFieldIsNull(user.getAge(), "You can't add user without age."
                + " Please, enter you age");
        checkIfUserHasInvalidAge(user);
    }

    private void checkIfFieldIsNull(Object fieldValue, String exceptionMessage) {
        if (fieldValue == null) {
            throw new NoValidDataException(exceptionMessage);
        }
    }

    private void checkIfUserHasInvalidLoginLength(User user) {
        int loginLength = user.getLogin().length();
        if (loginLength < VALID_LOGIN_LENGTH_MIN || loginLength > VALID_LOGIN_LENGTH_MAX) {
            throw new NoValidDataException("Incorrect login length. "
                    + "Login must contain at least " + VALID_LOGIN_LENGTH_MIN
                    + "  and less then " + VALID_LOGIN_LENGTH_MAX + " characters");
        }
    }

    private void checkCharactersInUsersLogin(User user) {
        boolean onlyLetters = true;
        char [] login = user.getLogin().toCharArray();
        for (char character : login) {
            if (!Character.isLetter(character)) {
                onlyLetters = false;
                break;
            }
        }
        if (!onlyLetters) {
            throw new NoValidDataException("Login must contain only characters");
        }
    }

    private void checkIfUserHasInvalidPasswordLength(User user) {
        int passwordLength = user.getPassword().length();
        if (passwordLength < VALID_PASSWORD_LENGTH_MIN
                || passwordLength > VALID_PASSWORD_LENGTH_MAX) {
            throw new NoValidDataException("Incorrect password length. "
                    + "Login must contain at least " + VALID_PASSWORD_LENGTH_MIN
                    + "  and less then " + VALID_PASSWORD_LENGTH_MAX + " characters");
        }
    }

    private void checkCharactersInUsersPassword(User user) {
        boolean onlyLettersAndDigits = true;
        char [] password = user.getPassword().toCharArray();
        for (char symbol : password) {
            if (!Character.isLetterOrDigit(symbol)) {
                onlyLettersAndDigits = false;
                break;
            }
        }
        if (!onlyLettersAndDigits) {
            throw new NoValidDataException(
                    "Password must contain only characters and digits");
        }
    }

    private void checkIfUserHasInvalidAge(User user) {
        if (user.getAge() < VALID_AGE) {
            throw new NoValidDataException("Service is available for " 
                    + VALID_AGE + "+ only");
        }
    }
}
