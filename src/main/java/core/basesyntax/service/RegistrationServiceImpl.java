package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getAge() == null
                || user.getAge() < 0 || user.getPassword() == null) {
            throw new RegistrationException("Invalid data!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exist!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least "
                    + MIN_AGE + " years!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least"
                    + MIN_PASSWORD_LENGTH + " symbols!");
        }
        return storageDao.add(user);
    }
}
