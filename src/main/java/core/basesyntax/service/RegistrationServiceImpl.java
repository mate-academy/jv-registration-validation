package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login length ned to be 6 or more symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User - " + user.getLogin() + " is already registered");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password length ned to be 6 or more symbols");
        }

        return storageDao.add(user);
    }
}
