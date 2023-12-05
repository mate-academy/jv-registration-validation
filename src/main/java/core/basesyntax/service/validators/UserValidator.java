package core.basesyntax.service.validators;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.RegistrationException;

public class UserValidator implements Validator<User> {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao;

    public UserValidator(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void validate(User user) {
        validateNullUser(user);
        validatePasswordAndLoginLength(user);
        validateAge(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login: " + user.getLogin() + " is already exist");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("Age must be greater than "
                    + MIN_USER_AGE
                    + ", current age: "
                    + user.getAge());
        }
    }

    private void validatePasswordAndLoginLength(User user) {
        if (user.getPassword()
                .length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password length should be at least "
                            + MIN_PASSWORD_LENGTH
                            + ", current length is: " + user.getPassword()
                            .length()
            );
        }
        if (user.getLogin()
                .length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login length should be at least "
                    + MIN_LOGIN_LENGTH
                    + ", current length is: " + user.getLogin()
                    .length()
            );
        }
    }

    private void validateNullUser(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login must be not null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password must be not null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age must be not null");
        }
    }
}
