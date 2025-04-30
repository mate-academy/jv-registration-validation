package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_SIZE = 6;
    private static final int MAX_LOGIN_SIZE = 25;
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MAX_PASSWORD_SIZE = 25;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        String login = user.getLogin();
        String password = user.getPassword();
        if (login == null) {
            throw new RegistrationException("Login can not be null!");
        }
        if (login.isEmpty()) {
            throw new RegistrationException("Login can not be empty!");
        }
        if (password == null) {
            throw new RegistrationException("User can not be null!");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password can not be empty!");
        }
        Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Age can not be null!");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login already exists!");
        }
        if (login.length() > MAX_LOGIN_SIZE
                || login.length() < MIN_LOGIN_SIZE) {
            throw new RegistrationException("Login length must be between "
                    + MIN_LOGIN_SIZE + " and " + MAX_LOGIN_SIZE);
        }
        if (password.length() > MAX_PASSWORD_SIZE
                || password.length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException("Password length must be between "
                    + MIN_PASSWORD_SIZE + " and " + MAX_PASSWORD_SIZE);
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new RegistrationException("Age must be between "
                    + MIN_AGE + " and " + MAX_AGE);
        }
        return storageDao.add(user);
    }
}
