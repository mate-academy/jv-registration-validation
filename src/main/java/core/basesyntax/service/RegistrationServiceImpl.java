package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGHT_PASS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("Login " + user.getLogin() + " has already exists");
            }
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGHT_PASS) {
            throw new RuntimeException("Password can't be less 6 symbols");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }

        return storageDao.add(user);
    }
}
