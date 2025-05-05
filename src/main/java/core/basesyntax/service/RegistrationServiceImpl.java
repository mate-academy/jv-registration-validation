package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CREDENTIAL_LENGTH = 6;
    private final StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }
        validateAge(user.getAge());
        String userLogin = user.getLogin();
        validateLogin(userLogin);
        checkIfLoginIsTaken(userLogin);
        validatePassword(user.getPassword());
        return storage.add(user);
    }

    private void validateAge(Integer userAge) {
        if (userAge == null || userAge < MIN_AGE) {
            String exceptionMessage =
                    userAge == null
                            ? "Age mustn't be null"
                            : String.format("You must be %d years old in order to register",
                            MIN_AGE);
            throw new RegistrationException(exceptionMessage);
        }
    }

    private void validateLogin(String userLogin) {
        if (userLogin == null || userLogin.length() < MIN_CREDENTIAL_LENGTH) {
            String exceptionMessage =
                    userLogin == null
                            ? "Login mustn't be null"
                            : String.format("Your login must be at least %d characters long",
                            MIN_CREDENTIAL_LENGTH);
            throw new RegistrationException(exceptionMessage);
        }
    }

    private void checkIfLoginIsTaken(String userLogin) {
        if (storage.get(userLogin) != null) {
            throw new RegistrationException(
                    "Such login already exists. Please choose a different one");
        }
    }

    private void validatePassword(String userPassword) {
        if (userPassword == null || userPassword.length() < MIN_CREDENTIAL_LENGTH) {
            String exceptionMessage =
                    userPassword == null
                            ? "Password mustn't be null"
                            : String.format("Your password must be at least %d characters long",
                            MIN_CREDENTIAL_LENGTH);
            throw new RegistrationException(exceptionMessage);
        }
    }
}
