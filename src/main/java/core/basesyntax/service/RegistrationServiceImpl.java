package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String SHORT_LENGTH_MESSAGE
            = " shouldn't contain less than six characters";
    private static final String NULL_DATA_MESSAGE = " can't be null";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User has no parameters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age" + NULL_DATA_MESSAGE);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login" + NULL_DATA_MESSAGE);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password" + NULL_DATA_MESSAGE);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login" + SHORT_LENGTH_MESSAGE);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password" + SHORT_LENGTH_MESSAGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already exists");
        }
        return storageDao.add(user);
    }
}
