package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be a null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException("Login can't be null or must be more than "
                    + MIN_LENGTH_LOGIN + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login already used: " + user.getLogin());
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Password can't be null or must be more than "
                    + MIN_LENGTH_PASSWORD + " characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
