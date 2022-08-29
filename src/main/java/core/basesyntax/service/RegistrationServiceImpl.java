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
        if (user == null) {
            throw new RuntimeException("User shouldn't be null.");
        }
        String login = user.getLogin();
        if (login.isBlank()) {
            throw new RuntimeException("User login shouldn't be null or empty.");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User already exists.");
        }
        Integer age = user.getAge();
        if (age == null || age < MIN_USER_AGE) {
            throw new RuntimeException("User's age shouldn't be null and less than 18.");
        }
        String password = user.getPassword();
        if (password.isBlank() || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password shouldn't be null and less than 6 chars.");
        }
        return storageDao.add(user);
    }
}
