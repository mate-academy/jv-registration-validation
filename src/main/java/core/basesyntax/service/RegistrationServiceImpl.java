package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("User login can not be empty");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can not be null");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new RuntimeException("User age need to have " + USER_MIN_AGE + "years");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password cannot be null");
        }
        if (user.getPassword().isBlank()) {
            throw new RuntimeException("User password cannot be empty");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Length of password is less than " + PASSWORD_MIN_LENGTH);
        }
        return storageDao.add(user);
    }
}
