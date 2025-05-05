package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MINIMAL_LENGTH) {
            throw new RegistrationException(
                    "login must consist of at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_LENGTH) {
            throw new RegistrationException(
                    "password must consist of at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException(
                    "registration is not available for users under 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("user with such login already exists");
        }
        return storageDao.add(user);
    }
}
