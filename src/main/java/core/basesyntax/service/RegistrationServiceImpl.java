package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!checkLogin(user)) {
            throw new UserRegistrationException("Invalid login");
        }
        if (!checkPassword(user)) {
            throw new UserRegistrationException("Invalid password");
        }
        if (!checkAge(user)) {
            throw new UserRegistrationException("Invalid age");
        }
        if (userExists(user)) {
            throw new UserRegistrationException("User already exists");
        }
        return storageDao.add(user);
    }

    protected boolean checkPassword(User user) {
        String password = user.getPassword();
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        return true;
    }

    protected boolean checkAge(User user) {
        Integer age = user.getAge();
        return age != null && age >= MIN_AGE;
    }

    protected boolean checkLogin(User user) {
        String login = user.getLogin();
        return login != null && login.length() >= MIN_LOGIN_LENGTH;
    }

    protected boolean userExists(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
