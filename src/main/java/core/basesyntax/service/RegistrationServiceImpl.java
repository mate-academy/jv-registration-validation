package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 120;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User cant be null");
        }
        passwordValidation(user);
        ageValidation(user);
        loginValidation(user);
        return storageDao.add(user);
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().isBlank() || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should contain at least 6 characters. "
                    + "Please try again.");
        }
    }

    private void ageValidation(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Please insert valid age");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("For registration your age must be at least 18 years old");
        }
    }

    private void loginValidation(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Please fill the field Login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exist, please try another");
        }
    }
}
