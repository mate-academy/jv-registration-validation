package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Enter user's login");
        }
        if (!(user.getLogin() != null && storageDao.get(user.getLogin()) == null)) {
            throw new RuntimeException("Storage already contains this user");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Enter user's age");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Enter user's password");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be at least " + MIN_AGE + " years old");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("User can't be older " + MAX_AGE + " years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must contains at least " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new RuntimeException("Password cannot be longer than "
                    + MAX_PASSWORD_LENGTH + " characters");
        }
        return storageDao.add(user);
    }
}
