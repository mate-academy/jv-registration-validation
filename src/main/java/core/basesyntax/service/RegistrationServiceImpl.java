package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        registrationValidation(user);
        storageDao.add(user);
        return user;
    }

    private void registrationValidation(User user) {
        if (user == null) {
            throw new InvalidRegistrationException("User cannot be 'null'");
        }

        String login = user.getLogin();
        if (storageDao.get(login) != null) {
            throw new InvalidRegistrationException("User with this login already exists "
                    + user.getLogin());
        }

        if (login == null) {
            throw new InvalidRegistrationException("Login cannot be null");
        }

        if (login.length() < MINIMAL_LOGIN_LENGTH) {
            throw new InvalidRegistrationException("Unacceptable login length: " + login.length());
        }

        String password = user.getPassword();
        if (password == null) {
            throw new InvalidRegistrationException("Password cannot be null");
        }

        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new InvalidRegistrationException("Unacceptable password length: "
                    + password.length());
        }

        Integer age = user.getAge();
        if (age == null) {
            throw new InvalidRegistrationException("Age cannot be null");
        }

        if (age < MINIMAL_AGE) {
            throw new InvalidRegistrationException("User is too young: " + age);
        }
    }
}
