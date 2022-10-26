package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        validateLogin(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is an user with such login in the Storage already");
        }
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private static void validateAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age. Age is less than minimum: " + MIN_AGE);
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Not valid age. Age is bigger than maximum" + MAX_AGE);
        }
    }

    private static void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password is less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    private static void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can't be empty string");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Login can't be full of whitespace characters");
        }
    }
}
