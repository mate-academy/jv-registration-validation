package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Can't register user, because user is null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The age of the user can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("The length of the login must be at least "
                    + MIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("The length of the password must be at least "
                    + MIN_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("The age of the user must be at least " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists with login "
                    + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }
}
