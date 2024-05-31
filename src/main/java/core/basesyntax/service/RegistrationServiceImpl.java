package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int LOGIN_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        register_NullUser_notOk(user);
        register_loginAlreadyExists_notOk(user);
        register_loginIsNull_notOk(user);
        validateLogin(user.getLogin());
        register_AgeIsNull_notOk(user);
        validateAge(user);
        register_passwordIsNull_notOk(user);
        validatePassword(user.getPassword());
        return storageDao.add(user);
    }

    private void register_NullUser_notOk(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
    }

    private void register_loginAlreadyExists_notOk(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login %s already exists!"
                    .formatted(user.getLogin()));
        }
    }

    private void register_loginIsNull_notOk(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null!");
        }
    }

    private void validateLogin(String login) {
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("Your Login should contain %s or more symbols!");
        }
    }

    private void register_AgeIsNull_notOk(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null!");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You age should be at least %d y.o."
                    .formatted(MIN_AGE));
        }
    }

    private void register_passwordIsNull_notOk(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Your password should contain 6 or more symbols!");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Your password should contain %s or more symbols!"
                    .formatted(PASSWORD_MIN_LENGTH));
        }
    }
}
