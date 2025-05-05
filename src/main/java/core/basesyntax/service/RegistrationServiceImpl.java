package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login is already exist");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("User must be at least "
                    + MINIMAL_AGE
                    + " years old");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must contain at least "
                    + MINIMAL_PASSWORD_LENGTH
                    + " characters");
        }
        return storageDao.add(user);
    }
}
