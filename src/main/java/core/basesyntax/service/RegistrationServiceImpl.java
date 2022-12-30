package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Login cannot be less than 18");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("there is user with such login in the Storage");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least six characters long");
        }
        return storageDao.add(user);
    }
}
