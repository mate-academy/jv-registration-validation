package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login length must be at least "
                    + MIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password length must be at least "
                    + MIN_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        for (User element : Storage.people) {
            if (user.getLogin().equals(element.getLogin())) {
                throw new RegistrationException("User with login: \"" + user.getLogin()
                        + "\" already exist");
            }
        }
        return storageDao.add(user);
    }
}
