package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_LENGTH = 6;
    private static final int LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login should be not null");
        }
        if (user.getLogin().length() < LOGIN_LENGTH) {
            throw new RegistrationException("Login length should be greater als " + LOGIN_LENGTH);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age should be not null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password should be not null");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RegistrationException("Password length should be greater als "
                    + PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User Age should be greater als " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This user exists in DB");
        }
        return storageDao.add(user);
    }
}
