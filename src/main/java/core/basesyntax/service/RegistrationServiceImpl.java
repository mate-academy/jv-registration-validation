package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistationException {
        if (user.getLogin() == null) {
            throw new RegistationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistationException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistationException("Not valid age:" + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistationException("Not valid password. Min "
                    + "allowed password length is "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistationException("Not valid login. Min "
                    + "allowed login length is "
                    + MIN_LOGIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistationException("Not valid login. User with this login is exist");
        }
        return storageDao.add(user);
    }
}
