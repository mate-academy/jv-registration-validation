package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserIsNotValidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserIsNotValidException {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new UserIsNotValidException("user is null");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_LENGTH) {
            throw new UserIsNotValidException(
                    "incorrect password. Password should have more than 6 elements");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new UserIsNotValidException("user is already registered");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < USER_AGE) {
            throw new UserIsNotValidException(
                    "incorrect age. User should have more than 18 year old");
        }
    }
}
