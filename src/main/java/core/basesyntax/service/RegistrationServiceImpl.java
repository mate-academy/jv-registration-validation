package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALUE_SIZE = 6;
    private static final int MIN_AGE = 18;
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
        if (login.length() < MIN_VALUE_SIZE) {
            throw new RegistrationException("Login length is too short!");
        }
        if (password.length() < MIN_VALUE_SIZE) {
            throw new RegistrationException("Password length is too short!");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Age is too short!");
        }
        return storageDao.add(user);
    }
}
