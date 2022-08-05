package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getPassword() == null || user.getLogin() == null) {
            throw new RuntimeException("Input data is invalid.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("The age of the user must be at least: "
                    + MIN_USER_AGE + " years old.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("The user password must be at least: "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
        if (user.getPassword().chars().filter(ch -> ch == ' ').count() != 0) {
            throw new RuntimeException("The password cannot contain space.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login"
                    + " is already exists in the Storage.");
        }
        return storageDao.add(user);
    }
}
