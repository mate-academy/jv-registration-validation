package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_REGISTRATION_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNewUserData(user);
        checkNewUserExistence(user);
        return storageDao.add(user);
    }

    private void checkNewUserData (User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can`t be null");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can`t be null");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("User`s age can`t be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Login length is less than " + MIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Password length is less than " + MIN_LENGTH);
        }
        if (user.getAge() < MIN_REGISTRATION_AGE) {
            throw new UserRegistrationException("User`s age is less than " + MIN_REGISTRATION_AGE);
        }
    }

    private void checkNewUserExistence (User user) {
        for (User person: Storage.people) {
            if (person.equals(user)) {
                throw new UserRegistrationException("User is already registered");
            }
        }
    }
}
