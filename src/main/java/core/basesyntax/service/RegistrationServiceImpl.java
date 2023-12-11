package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USERS_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("User's login must not be less than 6 characters");
        }

        if (user.getAge() < MIN_USERS_AGE) {
            throw new RegistrationException("User's age must not be less than 18");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("User's password must not be less than 6 characters");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The same login already exists");
        }
        storageDao.add(user);
        return user;
    }
}
