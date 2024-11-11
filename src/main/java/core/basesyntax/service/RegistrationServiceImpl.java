package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String LOGIN_ALREADY_TAKEN = "This login is already taken";
    private static final String LOGIN_TOO_SHORT = "Login cannot be null or less than 6 characters";
    private static final String PASSWORD_TOO_SHORT = "Password cannot be null "
            + "or less than 6 characters";
    private static final String AGE_TOO_YOUNG = "User's age must be at least 18 and cannot be null";

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserException(LOGIN_TOO_SHORT);
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserException(PASSWORD_TOO_SHORT);
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException(AGE_TOO_YOUNG);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException(LOGIN_ALREADY_TAKEN);
        }
        storageDao.add(user);
        return user;
    }
}
