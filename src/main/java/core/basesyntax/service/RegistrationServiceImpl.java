package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 18;
    private static final int MAX_LONG_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Null age");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Null Password");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("login null");
        }
        if (!(Storage.people.contains(user)) && user.getAge() >= MAX_AGE
                && user.getPassword().length() >= MAX_LONG_PASSWORD) {
            storageDao.add(user);
        } else {
            throw new RuntimeException("Invalid data");
        }
        return user;
    }
}
