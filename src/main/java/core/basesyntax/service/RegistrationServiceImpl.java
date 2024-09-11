package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Please enter the data");
        }
        if (validateLogin(user) && validatePassword(user)
                && validateAge(user) && loginCopyCheck(user)) {
            storageDao.add(user);
            return user;
        }
        throw new RegistrationException("Registration is not complete");
    }

    private boolean validateAge(User user) {
        if (user.getAge() == null
                || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Wrong age");
        }
        return true;
    }

    private boolean validateLogin(User user) {
        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Wrong login");
        }
        return true;
    }

    private boolean validatePassword(User user) {
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Wrong password");
        }
        return true;
    }

    private boolean loginCopyCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with the same login already exists.");
        }
        return true;
    }
}
