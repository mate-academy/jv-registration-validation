package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Object \"user\" must be NOT null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age of user must be NOT null");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new RuntimeException("Age of user must be at least " + USER_MIN_AGE + " years.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password of user must be NOT null");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Length of password must be at least " + PASSWORD_MIN_LENGTH
                    + " characters.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login of user must be NOT null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login of user must be NOT empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login \"" + user.getLogin()
                    + "\" has already been registered.");
        }
        user = storageDao.add(user);
        return user;
    }
}
