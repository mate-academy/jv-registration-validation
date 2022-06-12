package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || loginValidation(user.getLogin())
                || passwordValidation(user.getPassword()) || ageValidation(user.getAge())) {
            throw new RuntimeException("Invalid registration");
        }
        storageDao.add(user);
        return user;
    }

    private boolean loginValidation(String login) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is empty");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Login is blank");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login is already in use");
        }
        return true;
    }

    private boolean passwordValidation(String password) {
        if (password == null) {
            throw new RuntimeException("Password is empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be more than 6");
        }
        return true;
    }

    private boolean ageValidation(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age does not correct");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("Age must be more 18");
        }
        return true;
    }
}
