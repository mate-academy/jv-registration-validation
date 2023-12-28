package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user.getLogin() == null) {
            throw new UnacceptableUserExeption("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UnacceptableUserExeption("Login must be longer than six characters");
        }
        if (user.getPassword() == null) {
            throw new UnacceptableUserExeption("Password can't be null!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UnacceptableUserExeption("Password must be longer than six characters");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new UnacceptableUserExeption("Must be 18 years or older");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UnacceptableUserExeption("User login already exists");
        }
    }
}
