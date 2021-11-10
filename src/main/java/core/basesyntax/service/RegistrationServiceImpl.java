package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;

    @Override
    public User register(User user) {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        if (user == null) {
            throw new RuntimeException("User can't bu null");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new RuntimeException("Login can't be empty");
        }
        if (user.getPassword() == null || user.getPassword().length() <= MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must have at least 6 character");
        }
        if (user.getAge() == null || user.getAge() < MIN_VALID_AGE) {
            throw new RuntimeException("User's age must be at least 18 years");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exist");
        }
        storageDao.add(user);
        return user;
    }
}
