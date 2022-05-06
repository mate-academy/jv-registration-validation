package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't register null user!");
        }
        if (user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Can't register user with null data entered!");
        }
        if (user.getLogin().equals("")
                || user.getAge() < MINIMAL_AGE
                || user.getPassword().length() < MINIMAL_PASS_LENGTH
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Can't register user with invalid data entered!");
        }
        storageDao.add(user);
        return user;
    }
}
