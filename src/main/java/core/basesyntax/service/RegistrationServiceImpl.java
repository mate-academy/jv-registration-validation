package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login is null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password is null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The is such user with login" + user.getLogin());
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getAge()
                    + ". Min allowed login length is " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not valid password: " + user.getAge()
                    + ". Min allowed password length is " + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
