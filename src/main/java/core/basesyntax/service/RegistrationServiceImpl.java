package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 25;
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Login cannot be null!");
        }
        if (login.isEmpty()) {
            throw new RegistrationException("Login can't be empty");
        }
        if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters long and at most " + MAX_LOGIN_LENGTH);
        }
        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password cannot be null!");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password can't be empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters long and at most " + MAX_PASSWORD_LENGTH);
        }
        Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new RegistrationException("Age must be at least "
                    + MIN_AGE + " years old and at most " + MAX_AGE);
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
