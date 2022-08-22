package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MIN_VALUE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User must be initialized!");
        }
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new RuntimeException("User cann't have not initialized fields!");
        }
        if (user.getAge() < AGE_MIN_VALUE) {
            throw new RuntimeException("User's age is too small!");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("User's password must have at least 6 characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exist!");
        }
        if (user.getLogin().equals("") || user.getPassword().equals("")) {
            throw new RuntimeException("User's login or password cann't be empty!");
        }
        storageDao.add(user);
        return user;
    }
}
