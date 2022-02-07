package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Please write your login and password");
        }
    }

    public void validateLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user.getLogin() + " already exists");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("You must be at least 18 years old");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must be at least 6 characters");
        }
    }
}
