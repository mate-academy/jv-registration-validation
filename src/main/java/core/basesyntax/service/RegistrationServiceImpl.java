package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkValidUser(user);
        return storageDao.add(user);
    }

    private void checkValidUser(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("Login is shorter than valid length");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Password is shorter than valid length");
        }
        if (user.getAge() < VALID_AGE) {
            throw new RegistrationException("User is not adult");
        }
        for (User currentUser : Storage.people) {
            if (currentUser.equals(user)) {
                throw new RegistrationException("User already exists");
            }
        }
    }
}
