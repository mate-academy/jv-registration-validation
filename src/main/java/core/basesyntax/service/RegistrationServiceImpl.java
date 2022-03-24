package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_AGE = 120;
    private static final int MINIMUM_LENGTH_PASSWORD = 6;
    private static final int MAXIMUM_LENGTH_PASSWORD = 25;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        checkUser(user);
        storageDao.add(user);
        return user;
    }

    void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Empty data of user");
        }
        checkPassword(user);
        checkAge(user);
        checkLogin(user);
    }

    void checkPassword(User user) {
        if (user.getPassword().length() < MINIMUM_LENGTH_PASSWORD
                || user.getPassword().length() > MAXIMUM_LENGTH_PASSWORD
                || user.getPassword() == null) {
            throw new RuntimeException("Invalid password to registration");
        }
    }

    void checkAge(User user) {
        if (user.getAge() < MINIMUM_AGE
                || user.getAge() > MAXIMUM_AGE
                || user.getAge() == null) {
            throw new RuntimeException("Invalid age to registration");
        }
    }

    void checkLogin(User user) {
        if (storageDao.get(user.getLogin()) != null || user.getLogin() == null) {
            throw new RuntimeException("Invalid login to registration");
        }
    }
}
