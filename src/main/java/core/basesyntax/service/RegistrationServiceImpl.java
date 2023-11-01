package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_SIZE = 6;
    private static final int PASSWORD_MIN_SIZE = 6;
    private static final int MIN_AGE = 18;
    private static long id_count = 1;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }

        if (user.getLogin() == null) {
            throw new InvalidDataException("Login is null");
        }
        if (user.getLogin().length() < LOGIN_MIN_SIZE) {
            throw new InvalidDataException("Login is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Such login is already exist");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("Password is null");
        }
        if (user.getPassword().length() < PASSWORD_MIN_SIZE) {
            throw new InvalidDataException("Password is too short");
        }

        if (user.getAge() == null) {
            throw new InvalidDataException("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("You are underage");
        }

        user.setId(++id_count);
        return storageDao.add(user);
    }
}
