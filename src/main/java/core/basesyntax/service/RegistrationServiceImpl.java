package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Parameter User has a null value");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Field Age has a null value");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Field Password has a null value");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Field Login has a null value");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age less than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Password length less than " + MIN_LENGTH_PASSWORD);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Retrying user registration (login ["
                    + user.getLogin() + "] already exists)");
        }
        return storageDao.add(user);
    }
}
