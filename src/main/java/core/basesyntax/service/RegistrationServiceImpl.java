package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if ((user.getLogin() == null) || Objects.equals(user.getLogin(), "")) {
            throw new RuntimeException("Login is null or empty");
        }
        if (user.getPassword() == null || Objects.equals(user.getPassword(), "")) {
            throw new RuntimeException("Password is null or empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is a user with such a login in the Storage");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be at least 18 years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password must be at least 6 characters long");
        }
        storageDao.add(user);
        return user;
    }
}
