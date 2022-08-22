package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        Objects.requireNonNull(user, "User required");
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        Objects.requireNonNull(login, "Login required");
        if (login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with login - " + login + " exists");
        }
    }

    private void validatePassword(String password) {
        Objects.requireNonNull(password, "Password required");
        if (password.length() < 6) {
            throw new RuntimeException("Password length must be at least 6 characters. "
                    + "Current password length: " + password.length());
        }
    }

    private void validateAge(Integer age) {
        Objects.requireNonNull(age, "Age required");
        if (age <= 0 || age > 100) {
            throw new RuntimeException("Invalid age. Value can't be: " + age);
        }
        if (age < 18) {
            throw new RuntimeException("User must be at least 18 years old. Current age: " + age);
        }
    }
}
