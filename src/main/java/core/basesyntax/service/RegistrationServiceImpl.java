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
            throw new InvalidDataException("User's login is null!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("User's password is null!");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidDataException("The minimum length of the login must be 6!");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidDataException("The minimum length of the password must be 6!");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("Minimum registration age is 18 years!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Such a user is already registered!");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
