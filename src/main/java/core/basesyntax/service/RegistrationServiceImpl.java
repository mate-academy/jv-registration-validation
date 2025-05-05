package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_SIZE = 6;
    private static final int PASSWORD_MIN_SIZE = 6;
    private static final int MIN_AGE = 18;
    private static long id_count;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }

        checkLogin(user);
        checkPassword(user);
        checkAge(user);

        user.setId(++id_count);
        System.out.println(user.getId());
        return storageDao.add(user);
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login is null");
        }
        if (user.getLogin().length() < LOGIN_MIN_SIZE) {
            throw new InvalidDataException("Login is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Such login is already exist");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password is null");
        }
        if (user.getPassword().length() < PASSWORD_MIN_SIZE) {
            throw new InvalidDataException("Password is too short");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("You are underage");
        }
    }
}
