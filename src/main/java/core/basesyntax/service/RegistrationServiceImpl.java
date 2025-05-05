package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can`t be null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("The login field can't be null or empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user.getLogin() + " already exists");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must contain at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null || user.getAge() <= 0) {
            throw new RuntimeException("The age can`t be less than 0");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your age must be not less than " + MIN_AGE + " years");
        }
        return storageDao.add(user);
    }
}
