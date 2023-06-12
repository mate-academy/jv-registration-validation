package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The users login can not be duplicated");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("The login is null or has length less than "
                    + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("The password is null or has length less than "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("The age is null or less than " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
