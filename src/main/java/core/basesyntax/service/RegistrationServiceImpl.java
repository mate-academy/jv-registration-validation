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
        if (user.getPassword() == null || user.getPassword().length() < PASS_MIN_LENGTH) {
            throw new RuntimeException("Please enter your password. "
                    + "It should be at least 6 characters");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Please enter your login");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("You should be at least 18 years old");
        }
        StorageDaoImpl storageDao1 = new StorageDaoImpl();
        if (storageDao1.get(user.getLogin()) != null) {
            throw new RuntimeException("This user is already exist");
        }
        if (user.getPassword().matches(PASS_VALID_REGEX)) {
            return storageDao1.add(user);
        } else {
            throw new RuntimeException("Your password should contain at least 1"
                    + " uppercase letter, 1 lowercase letter and one digit");
        }
    }
}
