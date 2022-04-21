package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int AGE_MIN_VALUE = 18;
    public static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDaoImpl;

    public RegistrationServiceImpl(StorageDao storageDaoImpl) {
        this.storageDaoImpl = storageDaoImpl;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Fill user information with valid data."
                    + " User could not be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login could not be null. User " + user);
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age could not be null. User " + user);
        }
        if (user.getAge() > 0 && user.getAge() < AGE_MIN_VALUE) {
            throw new RuntimeException("You must be 18 years of age to register, but was :"
                    + user.getAge());
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password could nut be null.");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password length must be at least 6 characters, but was: "
                    + user.getPassword().length());
        }
        if (storageDaoImpl.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists.");
        }
        return storageDaoImpl.add(user);
    }
}
