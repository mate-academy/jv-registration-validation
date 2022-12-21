package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_SIZE_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user);
        checkUniqueLogin(user);
        checkPassword(user);

        return storageDao.add(user);
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can`t be null");
        }
        if (user.getPassword().trim().length() < MIN_SIZE_PASSWORD) {
            throw new RuntimeException("Password must be at least 6  "
                    + "character, don`t use whitespase before and after password");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You must be 18 years old to register");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can`t be null");
        }
        if (user.getLogin().trim().length() == 0) {
            throw new RuntimeException("Please, use some symbol for your login");
        }
    }

    private void checkUniqueLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("this login is already "
                    + "registered, use another login");
        }
    }
}
