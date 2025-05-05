package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRED_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputException("No data provided");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidInputException("Login is required to fill");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new InvalidInputException(
                    String.format(
                            "Your login should be at least %d characters long", MINIMUM_LENGTH));
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputException("User already exists");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidInputException("Password is required to fill");
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new InvalidInputException(
                    String.format(
                            "Your password should be at least %d characters long", MINIMUM_LENGTH));
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidInputException("Age is required to fill");
        }
        if (user.getAge() < REQUIRED_AGE) {
            throw new InvalidInputException(
                    String.format(
                            "You should be at least %d years old to login", REQUIRED_AGE));
        }
    }
}
