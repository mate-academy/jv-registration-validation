package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.utility.RegistrationException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private static final Integer MIN_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User should not be null");
        }
        validateUserLogin(user);
        validateUserAge(user);
        validateUserPassword(user);
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        }
        return user;
    }

    private void validateUserPassword(User user) {
        if (user.getPassword().length() < MIN_SYMBOLS) {
            throw new RegistrationException("User password should not be less than "
                    + MIN_SYMBOLS + " symbols");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException("User password should not be empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password should not be null");
        }
    }

    private void validateUserAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age should not be less than "
                    + MIN_AGE + " ages");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age should has null/empty value ");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("User age should not be negative number ");
        }
    }

    private void validateUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User login should not be null value ");
        }
        if (user.getLogin().length() < MIN_SYMBOLS) {
            throw new RegistrationException("User login should not be less than "
                    + MIN_SYMBOLS + " symbols");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("User login should not be empty");
        }
    }
}
