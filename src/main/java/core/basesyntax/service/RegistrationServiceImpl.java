package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validate(user);
        return storageDao.add(user);
    }

    private void validate(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
        validatePresentingInStorage(user);
    }

    private void validatePresentingInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    String.format("User with login \"%s\" is already in Storage", user.getLogin()));
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    String.format("Not valid password length: %d. Min allowed password length: %d",
                            user.getPassword().length(), MIN_PASSWORD_LENGTH));
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("User's age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    String.format("Not valid age: %d. Min allowed age: %d",
                            user.getAge(), MIN_AGE));
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login cannot be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    String.format("Not valid login length: %d. Min allowed login length: %d",
                            user.getLogin().length(), MIN_LOGIN_LENGTH));
        }
    }
}
