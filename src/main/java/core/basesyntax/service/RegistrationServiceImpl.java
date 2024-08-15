package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer REQUIRED_MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Registration failed, User can't be null");
        }
        checkUser(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login: " + user.getLogin()
                    + " is taken, try new one...");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Please write your login.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login is too short, "
                    + "it should have minimum 6 characters.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Please write a password.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is too short, "
                    + "it should have minimum 6 characters.");
        }
        if ((user.getAge() == null)) {
            throw new RegistrationException("Please write Your age.");
        }
        if ((user.getAge() < REQUIRED_MIN_AGE)) {
            throw new RegistrationException("You're too young.");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Your age can't be negative number");
        }
    }
}
