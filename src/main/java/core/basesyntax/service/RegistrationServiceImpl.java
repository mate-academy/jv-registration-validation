package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SYMBOLS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        boolean passwordIsValid = false;
        boolean loginIsValid = false;
        boolean ageIsValid = false;

        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age is null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null");
        }

        if (user.getLogin().length() >= MIN_SYMBOLS) {
            if (storageDao.get(user.getLogin()) == null) {
                loginIsValid = true;
            } else {
                throw new RegistrationException("A Different user uses this login!");
            }
        } else {
            throw new RegistrationException("Login is not valid!");

        }

        if (user.getPassword().length() >= MIN_SYMBOLS) {
            passwordIsValid = true;
        } else {
            throw new RegistrationException("Password is not valid!");

        }

        if (user.getAge() >= MIN_AGE) {
            ageIsValid = true;
        } else {
            throw new RegistrationException("Age is not valid!");

        }

        if (loginIsValid && passwordIsValid && ageIsValid) {
            storageDao.add(user);
        }

        return user;
    }
}
