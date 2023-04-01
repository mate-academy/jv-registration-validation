package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    static final int AGE_LIMIT = 18;
    static final int PASSWORD_LIMIT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new AuthenticationException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new AuthenticationException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new AuthenticationException("Password can't be null");
        }
        if (user.getId() == null) {
            throw new AuthenticationException("ID can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException("Login has already existed");
        }
        if (user.getAge() < AGE_LIMIT) {
            throw new AuthenticationException("Your age must be at least 18 y.o.");
        }
        if (user.getPassword().length() < PASSWORD_LIMIT) {
            throw new AuthenticationException("Password must be at least 6 characters long");
        }
        return storageDao.add(user);
    }
}
