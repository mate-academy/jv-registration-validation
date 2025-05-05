package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_MIN_INPUT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("Null user or user parameter");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).equals(user)) {
            throw new RegistrationException("User " + user + " already exist");
        }
        if (user.getLogin().length() < USER_MIN_INPUT) {
            throw new RegistrationException("User's login less than 6 characters");
        }
        if (user.getPassword().length() < USER_MIN_INPUT) {
            throw new RegistrationException("User's password less than 6 characters");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new RegistrationException("User is under 18 years old");
        }
        return storageDao.add(user);
    }
}
