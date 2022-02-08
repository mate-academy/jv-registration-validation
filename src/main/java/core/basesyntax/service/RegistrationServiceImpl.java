package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Data about user is null");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private boolean checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("User " + user + " don't have login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user + " with this login "
                    + user.getLogin() + " all ready in Storage");
        }
        return true;
    }

    private boolean checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("User " + user + " don't have password");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("User " + user + " password length to small");
        }
        return true;
    }

    private boolean checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("User don't have age");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("User must have age between 18 and 100");
        }
        return true;
    }
}
