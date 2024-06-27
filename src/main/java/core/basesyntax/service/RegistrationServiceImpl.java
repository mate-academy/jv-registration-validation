package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User is null.");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        storageDao.add(user);
        return user;
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (user.getLogin().equals("")) {
            throw new RegistrationException("Your login can't be an empty line.");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Your login should contain %s or more symbols."
                    .formatted(MIN_LENGTH));
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("There is an user with the same login.");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }
        if (user.getPassword().equals("")) {
            throw new RegistrationException("Your password can't be an empty line.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Your password should contain %s or more symbols."
                    .formatted(MIN_LENGTH));
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age of user shouldn't be less than  %s ."
                    .formatted(MIN_AGE));
        }
    }
}
