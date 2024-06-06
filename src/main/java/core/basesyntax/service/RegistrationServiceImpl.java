package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String USER_ALREADY_EXIST = "User with this login already registered";
    private static final String LOGIN_TOO_SHORT = "User's login must have at least 6 characters";
    private static final String FIELDS_CANNOT_BE_EMPTY = "All fields must be filled";
    private static final String PASSWORD_TOO_SHORT
            = "User's password must have at least 6 characters";
    private static final String AGE_TOO_LOW = "User's age must be at least 18 years old";
    private static final String INVALID_EMAIL = "Invalid email";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
    private static final int MIN_AGE = 18;
    private static final int MIN_NUMBER_OF_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(USER_ALREADY_EXIST);
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException(FIELDS_CANNOT_BE_EMPTY);
        }
        if (!isEmailValid(user.getLogin())) {
            throw new RegistrationException(INVALID_EMAIL);
        }
        if (user.getLogin().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException(LOGIN_TOO_SHORT);
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException(PASSWORD_TOO_SHORT);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(AGE_TOO_LOW);
        }

        return storageDao.add(user);
    }

    private boolean isEmailValid(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
