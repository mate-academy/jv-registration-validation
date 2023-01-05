package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_CHARACTERS_NUMBER = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login is already registered");
        }
        if (user.getPassword().length() < MIN_CHARACTERS_NUMBER) {
            throw new RuntimeException("Password's length is less then 6 characters");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("Not valid age");
        }
        return storageDao.add(user);
    }
}
