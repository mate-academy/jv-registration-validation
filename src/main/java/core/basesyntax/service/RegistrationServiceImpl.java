package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 60;
    private static final int MIN_SYMBOL = 7;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Empty data is not allowed.");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("User can not be younger 18 and older 60!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_SYMBOL) {
            throw new RuntimeException("Password must has 7 characters or more.");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Enter login. :)");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such login already exist");
        }
    }

}
