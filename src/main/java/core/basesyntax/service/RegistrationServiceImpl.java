package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("Can't register null user!");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null!");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidDataException("Not valid user's login " + user.getLogin()
                    + ". Min allowed login length is " + MINIMUM_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidDataException("Not valid user's password " + user.getPassword()
                    + ". Min allowed password length is " + MINIMUM_PASSWORD_LENGTH);
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("Not valid user's age " + user.getAge()
                    + ". Min allowed age is " + MINIMUM_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Such a user is already registered!");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
