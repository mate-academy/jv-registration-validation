package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationUserException("User can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException("This login used by another user");
        }
        validateAge(user);
        validateLogin(user);
        validatePassword(user);
    }

    private void validateAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationUserException("You must be over " + MIN_AGE + " to register!!!");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegistrationUserException("Minimal length of Login is " + MIN_CHARACTERS
                    + " symbols");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegistrationUserException("Minimal length of password is " + MIN_CHARACTERS
                    + " symbols");
        }
    }

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }
}
