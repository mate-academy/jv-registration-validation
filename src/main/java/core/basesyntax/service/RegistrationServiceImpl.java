package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null.");
        }

        checkUserLogin(user);
        checkUserPassword(user);
        checkUserAge(user);
        return storageDao.add(user);
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Min length of login should be"
                    + MIN_LOGIN_LENGTH + " characters."
                    + user.getLogin().length());
        }

        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new RegistrationException("Login can't start with number");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login: "
                    + user.getLogin()
                    + " does already exist.");
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Min length of password should be"
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("You have to enter your age.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Sorry. Age must be at least"
                    + MIN_AGE + " years old.");
        }
    }
}
