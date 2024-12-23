package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already used.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (user.getLogin().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            throw new RegistrationException("Login should contains at least 6 symbols.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            throw new RegistrationException("Password should contains at least 6 symbols.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Users under 18 years are not approved.");
        }
        storageDao.add(user);
        return user;
    }
}
