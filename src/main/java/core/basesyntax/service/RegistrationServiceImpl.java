package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_VALID_AGE = 18;
    private static final int MIN_USER_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Field can't be NULL");
        }
        if (user.getLogin().isEmpty()
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("Field can't be EMPTY");
        }
        if (user.getPassword().length() < MIN_USER_PASSWORD_LENGTH) {
            throw new RuntimeException("Min user password must be 6 characters");
        }
        if (user.getAge() < MIN_USER_VALID_AGE) {
            throw new RuntimeException("Minimum user age must be 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User's login is already exist");
        }
        return storageDao.add(user);
    }
}
