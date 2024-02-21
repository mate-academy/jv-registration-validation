package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private static final int MIN_LOGIN_CHARACTERS = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validation(user);
        return storageDao.add(user);
    }

    private void validation(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_CHARACTERS) {
            throw new RegistrationException("Login must be at least 6 character!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationException("Password must be at least 6 character!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User: " + user.getLogin() + "already registered");
        }
    }
}
