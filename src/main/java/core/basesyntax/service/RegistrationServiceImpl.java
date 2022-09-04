package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Not valid data");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age need to have " + MIN_AGE + "years");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Length of password is less than " + PASSWORD_MIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
