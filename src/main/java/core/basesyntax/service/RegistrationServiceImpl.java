package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AuthorisationError;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;

    @Override
    public User register(User user) throws AuthorisationError {
        if (user == null) {
            throw new AuthorisationError("Unknown error, Object is absent!");
        }
        String password = user.getPassword();
        if (password == null) {
            throw new AuthorisationError("Password must not be empty!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AuthorisationError("Password must be at least " + MIN_PASSWORD_LENGTH + " symbols.");
        }
        String login = user.getLogin();
        if (login == null) {
            throw new AuthorisationError("Login must have a value!");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new AuthorisationError("Age must be above or equal " + MIN_USER_AGE + " years old.");
        }
        if (storageDao.get(login) != null) {
            return null;
        }
        User result = storageDao.add(user);
        return result;

    }
}
