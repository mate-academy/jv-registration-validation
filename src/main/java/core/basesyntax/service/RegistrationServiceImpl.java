package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Age is less then " + MIN_USER_AGE);
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is less then "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login ["
                    + user.getLogin()
                    + "] already exist!");
        }
        return storageDao.add(user);
    }
}
