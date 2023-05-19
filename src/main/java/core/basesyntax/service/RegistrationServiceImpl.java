package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    public void validateUser(User user) {
        if (user == null) {
            throw new ValidationException("You should fill all necessary fields");
        }
    }

    public void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Login shouldn't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException(
                    "Your login " + user.getLogin() + " should have length "
                            + MIN_LOGIN_LENGTH + " or more");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException(
                    "Your login " + user.getLogin() + " is already taken by another user");
        }
    }

    public void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password shouldn't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException(
                    "Your password " + user.getPassword() + " should have length "
                            + MIN_PASSWORD_LENGTH + " or more");
        }
    }

    public void validateAge(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("Age shouldn't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User can't be under age " + MIN_AGE);
        }
    }
}
