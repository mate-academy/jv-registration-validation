package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR = 6;
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
            throw new RegistrationException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        if (user.getLogin().length() == 0 || user.getLogin().length() < MIN_CHAR) {
            throw new RegistrationException("Login length must be greater than 6");
        }

        if (user.getPassword().length() == 0 || user.getPassword().length() < MIN_CHAR) {
            throw new RegistrationException("Password length must be greater than 6");
        }

        if (user.getLogin().length() >= MIN_CHAR
                && user.getPassword().length() >= MIN_CHAR
                && user.getAge() >= MIN_AGE
                && storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        } else {
            throw new RegistrationException("User already exist in database");
        }
    }
}
