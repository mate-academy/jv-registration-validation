package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_SIZE_OF_PASSWORD_AND_LOGIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getLogin().length() < DEFAULT_SIZE_OF_PASSWORD_AND_LOGIN) {
            throw new OwnRuntimeException("Login must be longer then 6 symbol");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < DEFAULT_SIZE_OF_PASSWORD_AND_LOGIN) {
            throw new OwnRuntimeException("Password must be longer then 6 symbol");
        }
        if (user.getAge() < 18) {
            throw new OwnRuntimeException("You must be older then 18 years for registration");
        }
        if (storageDao.get(user.getLogin()) == null) {
            throw new OwnRuntimeException("Such user is registered");
        }
        storageDao.add(user);
        return user;
    }
}
