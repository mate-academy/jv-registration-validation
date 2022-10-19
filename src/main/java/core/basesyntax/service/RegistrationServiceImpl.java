package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 1;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User's login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User's age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User's password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password must be 6 characters or more");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User's age must be 18 or more");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RuntimeException("User's login must be 1 character's or more");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This User already exists in Storage");
        }
        return storageDao.add(user);
    }
}
