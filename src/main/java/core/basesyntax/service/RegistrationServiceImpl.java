package core.basesyntax.service;

import core.basesyntax.RegistrationServiceException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_AND_EMAIL_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("Can't register user, user is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("Can't register user, there is " + user.getLogin() + " login in Storage");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Can't register user, login is null");
        }
        if (user.getLogin().length() <= MIN_PASSWORD_AND_EMAIL_LENGTH) {
            throw new RegistrationServiceException("Can't register user, login " + user.getLogin() + " is to small");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Can't register user, password is null");
        }
        if (user.getPassword().length() <= MIN_PASSWORD_AND_EMAIL_LENGTH) {
            throw new RegistrationServiceException("Can't register user, password " + user.getPassword() + " is to small");
        }
        if (user.getAge() <= MIN_USER_AGE) {
            throw new RegistrationServiceException("Can't register user, age " + user.getAge() + " is to small");
        }
        storageDao.add(user);
        return user;
    }
}
