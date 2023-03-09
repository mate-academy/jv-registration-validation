package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AuthorizationError;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;

    @Override
    public User register(User user) throws AuthorizationError {
        if (user == null) {
            throw new AuthorizationError("Unknown error, Object is absent!");
        }
        String password = user.getPassword();
        if (password == null) {
            throw new AuthorizationError("Password must not be empty!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AuthorizationError("Password must be at least " + MIN_PASSWORD_LENGTH + " symbols.");
        }
        String login = user.getLogin();
        if (login == null) {
            throw new AuthorizationError("Login must be not null");
        }
        if (login.length() == 0) {
            throw new AuthorizationError("Login must have a value");
        }
        if (user.getAge() == null) {
            throw new AuthorizationError("Age must be not null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new AuthorizationError("Age must be above or equal " + MIN_USER_AGE + " years old.");
        }
        if (storageDao.get(login) != null) {
            throw new AuthorizationError("User with same login already exist in storage.");
        }
        return storageDao.add(user);
    }
}
