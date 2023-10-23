package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        try {
            checkNullValues(user);
            checkLength(user);
            checkValidAge(user);
            checkDB(user);
        } catch (RegistrationException e) {
            throw new RegistrationException();
        }
        return storageDao.add(user);
    }

    private boolean checkLength(User user) throws RegistrationException {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login length: "
                    + user.getLogin() + ". Min allowed login length is " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not valid login length: "
                    + user.getPassword() + ". Min allowed login length is " + MIN_PASSWORD_LENGTH);
        }
        return true;
    }

    private boolean checkValidAge(User user) throws RegistrationException {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        return true;
    }

    private boolean checkNullValues(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        return true;
    }

    private boolean checkDB(User user) throws RegistrationException {
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RegistrationException("User already exist");
        }
        if (Storage.people.contains(user)
                && Objects.equals(storageDao.get(user.getLogin()).getId(), user.getId())) {
            throw new RegistrationException("User already exist");
        }
        return true;
    }
}
