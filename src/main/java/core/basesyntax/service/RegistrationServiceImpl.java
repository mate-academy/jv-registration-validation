package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 116;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkIfUserExist(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkForNulls(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("User object is null");
        }
        if (user.getLogin() == null) {
            throw new InvalidRegistrationDataException("User login is null");
        }
        if (user.getAge() == null) {
            throw new InvalidRegistrationDataException("User age is null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().isEmpty()) {
            throw new InvalidRegistrationDataException("Login is empty "
                    + "or contains only whitespace");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() > MAX_AGE) {
            throw new InvalidRegistrationDataException("User age must be less than"
                    + " or equal to 116");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidRegistrationDataException("User age must be at least 18");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidRegistrationDataException("User password is not valid");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new InvalidRegistrationDataException("User password is less than 6 symbols");
        }
    }

    private void checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationDataException("User already exist");
        }
    }
}
