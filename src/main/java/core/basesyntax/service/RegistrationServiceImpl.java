package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User should not be of null value");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login should not be of null value");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password should not be of null value");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age should not be of null value");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new RegistrationException("User's login is less than 6 characters long");
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new RegistrationException("User's password is less than 6 characters long");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". User's age should be equal to or over " + MINIMUM_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login exists");
        }
        return storageDao.add(user);
    }
}
