package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_PASS_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("Argument cannot be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age is cannot be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getPassword().trim().equals("")) {
            throw new RegistrationException("Login cannot contain only whitespace");
        }
        if (user.getPassword().length() < MINIMAL_PASS_LENGTH) {
            throw new RegistrationException("Password must be longer than " + MINIMAL_PASS_LENGTH);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getLogin().trim().equals("")) {
            throw new RegistrationException("Login cannot contain only whitespace");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be longer than " + MINIMAL_LOGIN_LENGTH);
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Age should be bigger than " + MINIMAL_AGE);
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }

        return null;
    }
}
