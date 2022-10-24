package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already registered. Please try again.");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null. Try again.");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Password length must be more than "
                    + MIN_PASS_LENGTH + ".");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null. Try again.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your age is less than " + MIN_AGE + ".");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age can't be null. Try again.");
        }
        return storageDao.add(user);
    }
}
