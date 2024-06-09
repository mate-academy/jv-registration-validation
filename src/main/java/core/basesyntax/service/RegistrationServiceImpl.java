package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_AND_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        for (User userInLoop : Storage.people) {
            if (userInLoop.equals(user)) {
                throw new RegistrationException("User already exists");
            }
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_AND_PASSWORD_LENGTH) {
            throw new RegistrationException("Login length must be at least 6 characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LOGIN_AND_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length must be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        return storageDao.add(user);
    }
}
