package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);

        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Login can`t be null");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException(String
                    .format("User with given login \"%s\" already exists", login));
        }
        int loginLength = login.length();
        if (loginLength < MINIMAL_LENGTH) {
            throw new RegistrationException(String
                    .format("Login has to have at least %d characters,"
                            + " but was %d", MINIMAL_LENGTH, loginLength));
        }
        if (onlySpacesCheck(login)) {
            throw new RegistrationException("The login should not be composed"
                    + " entirely of whitespace characters.");
        }
    }

    private void validatePassword(User user) {
        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password can`t be null");
        }
        int passwordLength = password.length();
        if (passwordLength < MINIMAL_LENGTH) {
            throw new RegistrationException(String
                    .format("Password has to have at least %d characters,"
                            + " but was %d", MINIMAL_LENGTH, passwordLength));
        }
        if (onlySpacesCheck(password)) {
            throw new RegistrationException("The password should not be composed"
                    + " entirely of whitespace characters.");
        }
    }

    private void validateAge(User user) {
        Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Age can`t be null");
        }
        if (age < MINIMAL_AGE) {
            throw new RegistrationException(String
                    .format("Minimal age allowed - %d, but was %d", MINIMAL_AGE, age));
        }
    }

    private boolean onlySpacesCheck(String input) {
        String trimmedInput = input.trim();
        return trimmedInput.isEmpty();
    }
}
