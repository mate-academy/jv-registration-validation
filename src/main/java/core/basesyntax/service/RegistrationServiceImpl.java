package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }

        if (!isValidLogin(user)) {
            throw new RegistrationException("Invalid login format "
            + user.getLogin() + ". Min number of characters: " + MINIMAL_LOGIN_LENGTH);
        }

        if (!isValidPassword(user)) {
            throw new RegistrationException("Invalid login format "
                    + user.getLogin() + ". Min number of characters: " + MINIMAL_PASSWORD_LENGTH);
        }
        if(!isValidAge(user)) {
            throw new RegistrationException("Invalid login format "
                    + user.getLogin() + ". Min allowed age is " + MINIMAL_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }

        return storageDao.add(user);
    }
    private boolean isValidLogin(User user) {
        return  user.getLogin().length() >= MINIMAL_LOGIN_LENGTH;
    }
    private boolean isValidPassword(User user) {
        return user.getPassword().length() >= MINIMAL_PASSWORD_LENGTH;
    }
    private boolean isValidAge(User user) {
        return user.getAge() >= MINIMAL_AGE;
    }

}
