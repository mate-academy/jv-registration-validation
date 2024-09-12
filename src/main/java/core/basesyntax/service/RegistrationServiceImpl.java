package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with this login already exists");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("The login length cannot be "
                    + "shorter than " + MINIMAL_LOGIN_LENGTH + " characters");
        }
        if (user.getLogin().contains(" ")) {
            throw new RegistrationException("Login should not contain spaces");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("The password length "
                    + "cannot be shorter than " + MINIMAL_PASSWORD_LENGTH + " characters");
        }
        if (user.getPassword().contains(" ")) {
            throw new RegistrationException("Password should not "
                    + "contain spaces");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Registration is available to "
                    + "users over " + MINIMAL_AGE + " years of age");
        }
        return storageDao.add(user);
    }
}
