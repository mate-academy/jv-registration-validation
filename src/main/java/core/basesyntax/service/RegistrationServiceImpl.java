package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        addOriginUser(user);
        return user;
    }

    private static void checkLogin(User user) {
        String exceptionMessage = "User login length should be at least 6 characters.";
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException(exceptionMessage);
        }
        if (user.getLogin().isBlank()) {
            throw new RegistrationException(exceptionMessage);
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException(exceptionMessage);
        }

    }

    private static void checkPassword(User user) {
        String exceptionMessage = "User password length should be at least 6 characters.";
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException(exceptionMessage);
        }
        if (user.getPassword().isBlank()) {
            throw new RegistrationException(exceptionMessage);
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException(exceptionMessage);
        }
    }

    private static void checkAge(User user) {
        if (user.getAge() < 18) {
            throw new RegistrationException("User age should be 18 or more");
        }
    }

    private void addOriginUser(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        } else {
            throw new RegistrationException("User with login \"" + user.getLogin()
                    + "\" already exists in the storage");
        }
    }
}
