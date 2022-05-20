package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int ADULT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User's object is null");
        }
        if (user.getAge() == null || user.getPassword() == null
                                  || user.getLogin() == null) {
            throw new RuntimeException("User's age, login or password cannot be null!");
        }
        if (user.getAge() < ADULT_AGE) {
            throw new RuntimeException("Age must be more than " + ADULT_AGE);
        }
        if (user.getPassword().isBlank()) {
            throw new RuntimeException("Password field is blank");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password to short. Must be more "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Login field is blank");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login already exist");
        }
        return storageDao.add(user);
    }
}
