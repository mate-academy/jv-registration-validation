package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_REGISTRATION_AGE = 18;
    private static final Integer MAX_REGISTRATION_AGE = 100;
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storage;

    public RegistrationServiceImpl() {
        storage = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        checkIsUserValid(user);
        checkIsLoginValid(user);
        checkIsPasswordValid(user);
        checkIsAgeValid(user);
        return storage.add(user);
    }

    private void checkIsUserValid(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
    }

    private void checkIsLoginValid(User user) {
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("User been register early");
        }
    }

    private void checkIsPasswordValid(User user) {
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length is less than 6");
        }
    }

    private void checkIsAgeValid(User user) {
        if (user.getAge() == null
                || user.getAge() < MIN_REGISTRATION_AGE
                || user.getAge() > MAX_REGISTRATION_AGE) {
            throw new RuntimeException("Invalid user age");
        }
    }
}

