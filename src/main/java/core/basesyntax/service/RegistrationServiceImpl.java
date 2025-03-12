package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN = 6;
    private static final int PASSWORD_MIN = 6;
    private static final int AGE_MIN = 18;
    private final String login = "validLogin";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getLogin().equals(login)) {
            throw new RegistrationException("User with this login " + login + " already exists.");
        }
        if (user.getLogin().length() < LOGIN_MIN) {
            throw new RegistrationException("Login can't be less symbol than " + LOGIN_MIN);
        }
        if (user.getLogin().length() >= LOGIN_MIN) {
            storageDao.add(user);
        }
        if (user.getPassword().length() < PASSWORD_MIN) {
            throw new RegistrationException("Password can't be less symbol than " + PASSWORD_MIN);
        }
        if (user.getPassword().length() >= PASSWORD_MIN) {
            storageDao.add(user);
        }
        if (user.getAge() >= AGE_MIN) {
            storageDao.add(user);
        }
        if (user.getAge() < AGE_MIN) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + AGE_MIN);
        }
        return storageDao.add(user);
    }
}

