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
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user doesn't exist");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
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
            throw new RegistrationException("User with login %s already exists."
                    .formatted(user.getLogin()));
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Your password can't be null.");
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
            throw new RegistrationException("Your age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You age should be at least %d y.o."
                    .formatted(MIN_AGE));
        }
    }
}
