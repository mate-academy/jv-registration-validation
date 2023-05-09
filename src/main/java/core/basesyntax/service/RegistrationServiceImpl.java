package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkBeforeAdding(user);
        return storageDao.add(user);
    }

    private void checkBeforeAdding(User user) {
        checkUser(user);
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
    }

    private void checkLogin(String userLogin) {
        if (userLogin == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (storageDao.get(userLogin) != null) {
            throw new InvalidDataException("Login is already taken");
        }
    }

    private void checkAge(Integer userAge) {
        if (userAge == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (userAge < MIN_AGE) {
            throw new InvalidDataException("Age can't be less than:" + MIN_AGE);
        }
    }

    private void checkPassword(String userPassword) {
        if (userPassword == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (userPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password can't be shorter than:"
                    + MIN_PASSWORD_LENGTH);
        }
    }
}
