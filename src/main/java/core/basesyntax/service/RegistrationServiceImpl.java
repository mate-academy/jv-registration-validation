package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 3;
    private static final int LOGIN_MAX_LENGTH = 20;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 25;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login should not be null.");
        }
        if (user.getLogin().length() > 0 && user.getLogin().isBlank()) {
            throw new InvalidUserDataException("Login should not be blank.");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidUserDataException("Login should not be empty.");
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new InvalidUserDataException("User login should be at least " + LOGIN_MIN_LENGTH
                    + " characters.");
        }
        if (user.getLogin().length() > LOGIN_MAX_LENGTH) {
            throw new InvalidUserDataException("User login should not be more than "
                    + LOGIN_MAX_LENGTH + " characters.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with login " + user.getLogin()
                    + " is already registered.");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password should not be null.");
        }
        if (user.getPassword().length() > 0 && user.getPassword().isBlank()) {
            throw new InvalidUserDataException("Password should not be blank.");
        }
        if (user.getPassword().isEmpty()) {
            throw new InvalidUserDataException("Password should not be empty.");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidUserDataException("Password should be at least "
                    + PASSWORD_MIN_LENGTH + " characters.");
        }
        if (user.getPassword().length() > PASSWORD_MAX_LENGTH) {
            throw new InvalidUserDataException("Password should not be more than "
                    + PASSWORD_MAX_LENGTH + " characters.");
        }
        if (user.getAge() == null) {
            throw new InvalidUserDataException("User age should not be null.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User age should not be less than "
                    + MIN_AGE + ".");
        }
        if (user.getAge() > MAX_AGE) {
            throw new InvalidUserDataException("User age should not be greater than "
                    + MAX_AGE + ".");
        }
        return storageDao.add(user);
    }
}
