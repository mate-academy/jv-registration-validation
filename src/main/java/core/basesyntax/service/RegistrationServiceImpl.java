package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User's login has to be at least "
                    + MIN_LOGIN_LENGTH + " characters!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's password  has to be at least "
                    + MIN_PASSWORD_LENGTH + " characters!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age should be at least "
                    + MIN_AGE + " years old!");
        }
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        return user;
    }
}
