package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User's login can not be null");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("User's login can not be blank");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User's password can not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password must be at least 6 symbols");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User's age can not be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User's age must be at least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
