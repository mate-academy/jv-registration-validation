package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS_FOR_LOGIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User input was null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password was null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exists. "
                    + "Try another login");
        }
        if (user.getLogin().length() < MIN_CHARACTERS_FOR_LOGIN_PASSWORD) {
            throw new RegistrationException("User login length is less than 6");
        }
        if (user.getPassword().length() < MIN_CHARACTERS_FOR_LOGIN_PASSWORD) {
            throw new RegistrationException("User password length is less than 6");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age was not null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age less than 18");
        }
        return storageDao.add(user);
    }
}


