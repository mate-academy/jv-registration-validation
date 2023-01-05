package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new InvalidDataException("Can`t register null user");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Can`t register user with null login");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("Can`t register user with empty login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This login already exist in storage");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Can`t register user with null password");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException(
                    String.format("Can`t register user with password length less than %d",
                            MIN_PASSWORD_LENGTH));
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Can`t register user with null age");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException(
                    String.format("Can`t register user with age less than %d",
                            MIN_AGE));
        }
    }
}
