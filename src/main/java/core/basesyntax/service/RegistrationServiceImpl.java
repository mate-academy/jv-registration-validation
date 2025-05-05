package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int PASS_MIN_LENGTH = 6;
    public static final String PASS_VALID_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        passwordValidation(user.getPassword());
        loginValidation(user.getLogin());
        ageValidation(user.getAge());
        return storageDao.add(user);
    }

    private void passwordValidation(String pass) {
        if (pass == null || !pass.matches(PASS_VALID_REGEX)) {
            throw new RuntimeException("Please enter your password. "
                    + "It should be at least 6 characters and contains at least"
                    + "one uppercase, one lowercase, one number");
        }
    }

    private void loginValidation(String log) {
        if (log == null || storageDao.get(log) != null) {
            throw new RuntimeException("Incorrect login or user is already exist");
        }
    }

    private void ageValidation(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new RuntimeException("You should be at least 18 years old");
        }
    }
}
