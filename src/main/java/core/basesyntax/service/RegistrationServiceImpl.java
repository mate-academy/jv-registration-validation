package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 4;
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can not be NULL");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("User login can not be NULL");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("User password can not be NULL");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("User age can not be NULL");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user already exists in the storage");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new RuntimeException("User login length must be minimum "
                    + MINIMUM_LOGIN_LENGTH + " letters but is " + user.getLogin().length());
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("User password length must be minimum "
                    + MINIMUM_PASSWORD_LENGTH + " letters but is " + user.getPassword().length());
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("User age can not be least " + MINIMUM_AGE
                    + " years old but is " + user.getAge());
        }
        storageDao.add(user);
        return user;
    }
}
