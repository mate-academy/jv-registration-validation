package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login shouldn't contain less than six characters");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password shouldn't contain less than six characters");
        }
        for (User user1 : Storage.people) {
            if (user1.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with this login is already exists");
            }
        }
        return storageDao.add(user);
    }
}

