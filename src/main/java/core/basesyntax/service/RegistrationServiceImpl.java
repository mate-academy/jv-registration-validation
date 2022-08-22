package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_REGISTRATION_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getAge() == null || user.getAge() < MIN_REGISTRATION_AGE) {
            throw new RuntimeException("User`s age cannot be less than " + MIN_REGISTRATION_AGE);
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new RuntimeException("User`s login cannot be empty or null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getPassword().isBlank()) {
            throw new RuntimeException("Password length cannot be less than " + MIN_PASSWORD_LENGTH
                    + " and password cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User by login: " + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}
