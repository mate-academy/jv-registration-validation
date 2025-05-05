package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("Object user must be not null");
        }
        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password must not be empty!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " symbols.");
        }
        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Login must be not null");
        }
        if (login.length() == 0) {
            throw new RegistrationException("Login must have a value");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age must be not null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("Age must be above or equal "
                    + MIN_USER_AGE + " years old.");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with same login already exist in storage.");
        }
        return storageDao.add(user);
    }
}
