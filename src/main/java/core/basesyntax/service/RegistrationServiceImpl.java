package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int ZERO = 0;
    private final StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't register null user");
        }
        checkForNullData(user);
        checkPasswordLength(user.getPassword());
        checkAge(user.getAge());
        if (storage.get(user.getLogin()) == null) {
            storage.add(user);
        }
        return user;
    }

    private void checkForNullData(User user) {
        if (user.getLogin() == null) {
            throw new NullDataException("login");
        } else if (user.getPassword() == null) {
            throw new NullDataException("password");
        } else if (user.getAge() == null) {
            throw new NullDataException("age");
        }
    }

    private void checkPasswordLength(String password) {
        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new PasswordLengthException();
        }
    }

    private void checkAge(int age) {
        if (age <= ZERO) {
            throw new InvalidAgeException("Can't register user with negative or zero age");
        } else if (age < MINIMAL_AGE) {
            throw new InvalidAgeException("Can't register user whose age is under 18");
        }
    }
}
