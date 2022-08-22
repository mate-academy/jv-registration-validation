package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_ALLOWED_AGE = 18;
    private static final int MINIMUM_ALLOWED_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Null user exception");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Null login exception");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Empty login exception");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Null password exception");
        }
        if (user.getPassword().isBlank()) {
            throw new RuntimeException("Empty password exception");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exist");
        }
        if (user.getAge() < MINIMUM_ALLOWED_AGE) {
            throw new RuntimeException("User age is under 18");
        }
        if (user.getPassword() != null
                && user.getPassword().length() < MINIMUM_ALLOWED_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid password length");
        }
        storageDao.add(user);
        return user;
    }
}
