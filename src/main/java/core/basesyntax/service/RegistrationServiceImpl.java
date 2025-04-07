package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
        public User register(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Invalid age, minimum allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "Login is too short, the minimum length is " + MIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "Password is too short, the minimum length is " + MIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login already exist in storage");
        }
        return storageDao.add(user);
    }
}
