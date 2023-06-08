package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User currentUser : Storage.people) {
            if (user.getLogin().equals(currentUser.getLogin())) {
                throw new UserRegistrationException("User already exists");
            }
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationException("Login is incorrect. Min allowed login is "
                    + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password is incorrect. Min allowed password is "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
