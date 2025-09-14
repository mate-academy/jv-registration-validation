package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN = 6;
    private static final int MIN_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can not be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("duplicated user");
        }

        if (user.getLogin().length() < MIN_LOGIN) {
            throw new RegistrationException("login must be more then 6 digits");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password can not be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RegistrationException("password must be more then 6 digits");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age can not be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be more then 18");
        }

        return user;
    }
}
