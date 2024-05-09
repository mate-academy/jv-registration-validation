package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
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
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("All fields must be filled");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already registered");
        }
        if (user.getLogin().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("Username must be at least 6 characters long");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age cannot be less than " + MIN_AGE);
        }
        if (!isEmailValid(user.getLogin())) {
            throw new RegistrationException("Invalid email");
        }

        return storageDao.add(user);
    }

    private boolean isEmailValid(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
