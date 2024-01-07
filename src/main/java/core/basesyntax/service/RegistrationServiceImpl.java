package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login "
                    + user.getLogin() + " already exists!");
        }
        if (!isValidLoginLength(user.getLogin())) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters long!");
        }
        if (!isValidPasswordLength(user.getPassword())) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters long!");
        }
        if (!isValidAge(user.getAge())) {
            throw new RegistrationException("Age must be at least "
                    + MIN_AGE + " years!");
        }
        return storageDao.add(user);
    }

    private boolean isValidLoginLength(String login) {
        if (login == null) {
            return false;
        }
        return login.length() >= MIN_LOGIN_LENGTH;
    }

    private boolean isValidPasswordLength(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isValidAge(Integer age) {
        if (age == null) {
            return false;
        }
        return age >= MIN_AGE;
    }
}
