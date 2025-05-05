package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import exception.RegistrationValidationException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationValidationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationValidationException("Age can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationValidationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationValidationException("Age must be at least "
                    + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidationException("Login \""
                    + user.getLogin() + "\" already exists");
        }
        return storageDao.add(user);
    }
}
