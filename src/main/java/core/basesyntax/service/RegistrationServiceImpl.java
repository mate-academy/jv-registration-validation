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
            throw new NullPointerException("User is null");
        }

        if (user.getLogin() != null && user.getLogin().length() >= MIN_SYMBOLS) {
            if (storageDao.get(user.getLogin()) == null) {
                loginIsValid = true;
            }
        } else {
            throw new RegistrationException("Login is not valid "
                    + "or different user uses this login!");
        }

        if (user.getPassword() != null && user.getPassword().length() >= MIN_SYMBOLS) {
            passwordIsValid = true;
        } else {
            throw new RegistrationException("Password is not valid!");
        }

        if (user.getAge() != null && user.getAge() >= MIN_AGE) {
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
