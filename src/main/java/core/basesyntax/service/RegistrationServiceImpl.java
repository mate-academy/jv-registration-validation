package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t be null");
        } else if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    +
                    ". Min allowed login length is " + MIN_LENGTH);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        } else if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Not valid password: " + user.getPassword()
                    +
                    ". Min allowed password length is " + MIN_LENGTH);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can`t be null");
        } else if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    +
                    ". Min allowed age is " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User login can't be repeated");
        }
        return storageDao.add(user);
    }
}
