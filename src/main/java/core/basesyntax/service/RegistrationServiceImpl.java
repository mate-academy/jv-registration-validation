package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_LOGIN_LENGTH_MIN = 6;
    private static final int USER_PASSWORD_LENGTH_MIN = 6;
    private static final int USER_AGE_MIN = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be empty");
        }
        if (user.getLogin().length() < USER_LOGIN_LENGTH_MIN) {
            throw new RegistrationException("Your login must be %d or more characters"
                                                    .formatted(USER_LOGIN_LENGTH_MIN));
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login :%s already exists"
                                                    .formatted(user.getLogin()));
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException("Password can't be empty");
        }
        if (user.getPassword().length() < USER_PASSWORD_LENGTH_MIN) {
            throw new RegistrationException("Your password must be %d or more characters"
                                                    .formatted(USER_PASSWORD_LENGTH_MIN));
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < USER_AGE_MIN) {
            throw new RegistrationException("Your age must be %d or bigger"
                                                    .formatted(USER_AGE_MIN));
        }
    }
}
