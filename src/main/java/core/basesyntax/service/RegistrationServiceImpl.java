package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        return checkUser(user);
    }

    private User checkUser(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age lower than min age: " + MIN_AGE
                    + "\nBut it was: " + user.getAge());
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password shorter than min length"
                    + "\nIt was: " + user.getPassword().length()
                    + "\nNeed longer than: " + (MIN_PASSWORD_LENGTH - 1));
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login was: " + user.getLogin());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this Login Exist");
        }
        return storageDao.add(user);
    }
}
