package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Not valid age");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RuntimeException("Not valid password");
        }
        if (user.getId() == null) {
            throw new RuntimeException("ID can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The same login");
        }
        return storageDao.add(user);
    }
}
