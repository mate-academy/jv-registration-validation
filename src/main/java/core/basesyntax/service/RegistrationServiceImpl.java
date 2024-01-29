package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_AND_LOGIN_INDEX = 6;
    private static final int MIN_AGE_INDEX = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("The login can't be null.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The age can't be null.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password can't be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_AND_LOGIN_INDEX) {
            throw new RegistrationException("User's password must be at least 6 characters.");
        }
        if (user.getLogin().length() < MIN_PASSWORD_AND_LOGIN_INDEX) {
            throw new RegistrationException("User's login must be at least 6 characters.");
        }
        if (user.getAge() < MIN_AGE_INDEX) {
            throw new RegistrationException("User's age must be at least 18 years old.");
        }
        User checkedUsersLoginInStorage = storageDao.get(user.getLogin());
        if (checkedUsersLoginInStorage != null) {
            throw new RegistrationException("This login is already in use. Try to choose another login.");
        }
        return storageDao.add(user);
    }
}
