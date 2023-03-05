package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 90;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User should be not null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with same login exist");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new InvalidDataException("Login should be not null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age must be not null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password should be not null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new InvalidDataException("Not valid age");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Password should be more than 6");
        }
        return storageDao.add(user);
    }
}
