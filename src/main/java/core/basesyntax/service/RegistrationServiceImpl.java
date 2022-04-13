package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_LOGIN = 3;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE_USER = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can`t be null");
        }
        if (user.getLogin().isEmpty()
                || user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RuntimeException("Login can`t be empty or length over " + MIN_LENGTH_LOGIN);
        }
        if (user.getPassword().isEmpty()
                || user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Password must be over " + MIN_LENGTH_PASSWORD + " symbols");
        }
        if (user.getAge() < MIN_AGE_USER) {
            throw new RuntimeException("Age must be over " + MIN_AGE_USER);
        }
        storageDao.add(user);
        return user;
    }
}
