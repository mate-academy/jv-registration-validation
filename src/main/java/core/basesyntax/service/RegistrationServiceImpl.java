package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_SYMBOLS = 6;
    private static final int MIN_REGISTRATION_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null
                || user.getAge() == 0) {
            throw new RuntimeException("You have empty lines. Please pass correct data");
        }
        if (user.getAge() < MIN_REGISTRATION_AGE) {
            throw new RuntimeException("Your age must be greater or equal than "
                    + MIN_REGISTRATION_AGE);
        }
        if (user.getPassword().length() < MIN_LOGIN_SYMBOLS) {
            throw new RuntimeException("Your password must be greater than "
                    + MIN_LOGIN_SYMBOLS + " symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user has been already registered");
        }
        return storageDao.add(user);
    }
}
