package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 110;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password can't be less than 6 characters");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login can't be less than 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE
                    + ". Max allowed age is " + MAX_AGE);
        }
        if (!isUserAbsent(user)) {
            throw new RegistrationException("A user with this login already exists");
        }
        if (isUserAbsent(user)
                && user.getLogin().length() >= MIN_LOGIN_LENGTH
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && user.getAge() >= MIN_AGE && user.getAge() <= MAX_AGE) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean isUserAbsent(User user) {
        for (User entry : Storage.people) {
            if (entry.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        return true;
    }
}
