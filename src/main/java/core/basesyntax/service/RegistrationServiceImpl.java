package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN = 18;
    private static final int PASSWORD_LENGTH_MIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationValidationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationValidationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationValidationException("Age can't be null");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH_MIN) {
            throw new RegistrationValidationException("Password can't be less than "
                    + PASSWORD_LENGTH_MIN);
        }
        if (user.getAge() < AGE_MIN) {
            throw new RegistrationValidationException("Age can't be less than "
                    + AGE_MIN);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidationException("Login " + user.getLogin()
                    + " already exist!");
        }
        return storageDao.add(user);
    }
}
