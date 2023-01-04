package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LENGTH_PASSWORD = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkNull(User user) {
        if (user == null) {
            throw new InvalidDataException("Can`t register null user");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Can`t register user with null login");
        }
        if (user.getLogin().length() == 0) {
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
        if (user.getPassword().length() < VALID_LENGTH_PASSWORD) {
            throw new InvalidDataException(
                    String.format("Can`t register user with password length less than %d",
                            VALID_LENGTH_PASSWORD));
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Can`t register user with null age");
        }
        if (user.getAge() < VALID_AGE) {
            throw new InvalidDataException(
                    String.format("Can`t register user with age less than %d",
                            VALID_AGE));
        }
    }
}
