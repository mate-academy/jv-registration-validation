package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RegistrationException("User data cannot be null or contain empty fields");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age must be at least " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login length must be no less than "
                    + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length must be no less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
