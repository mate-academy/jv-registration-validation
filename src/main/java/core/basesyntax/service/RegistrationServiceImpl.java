package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login is not correct. "
                    + "The login should contain more than 6 symbols");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password is not correct. "
                    + "The password should contain more than 6 symbols.");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("You must be over 18!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
