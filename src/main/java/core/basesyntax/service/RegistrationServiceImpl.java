package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AMOUNT_OF_AGE = 18;
    private static final int MINIMAL_AMOUNT_OF_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("You can not add user without any parameters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < MINIMAL_AMOUNT_OF_SYMBOLS) {
            throw new RegistrationException("Login should have at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_AMOUNT_OF_SYMBOLS) {
            throw new RegistrationException("Password should have at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AMOUNT_OF_AGE) {
            throw new RegistrationException("User need to be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
