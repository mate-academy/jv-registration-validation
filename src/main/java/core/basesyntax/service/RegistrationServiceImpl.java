package core.basesyntax.exception;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_AMOUNT_OF_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user doesn't exist");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("User login field is null");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("User password field is null");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("User age field is null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already exist");
        }

        if (user.getLogin().length() < MIN_AMOUNT_OF_SYMBOLS) {
            throw new RegistrationException("Login is short");
        }

        if (user.getPassword().length() < MIN_AMOUNT_OF_SYMBOLS) {
            throw new RegistrationException("Password is short");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User is so young for registration");
        }
        return storageDao.add(user);
    }
}
