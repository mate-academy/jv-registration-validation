package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_AGE = 18;
    private static final int MAX_VALID_AGE = 110;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMPTY_LINE = "";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null.");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null.");
        }
        if (user.getAge() == null) {
            throw new ValidationException("Age can't be null.");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("This login already exists.");
        }
        if (user.getPassword().equals(EMPTY_LINE)) {
            throw new ValidationException("Password can't be empty.");
        }
        if (user.getLogin().equals(EMPTY_LINE)) {
            throw new ValidationException("Login can't be empty.");
        }
        if (user.getAge() < MIN_VALID_AGE || user.getAge() > MAX_VALID_AGE) {
            throw new ValidationException("Age of user is invalid.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password must contains at least 6 characters.");
        }
    }
}
