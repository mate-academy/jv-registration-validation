package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The \"Login\" field cannot be empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The \"Password\" field cannot be empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The \"Age\" field cannot be empty");
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
