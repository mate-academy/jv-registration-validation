package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("User login can not be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login "
                    + user.getLogin() + " already exists!");
        }

        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("User's age should be bigger then "
                    + MIN_USER_AGE + " years!");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("User's password should be longer then "
                    + MIN_LENGTH_PASSWORD + " characters!");
        }

        return storageDao.add(user);
    }
}
