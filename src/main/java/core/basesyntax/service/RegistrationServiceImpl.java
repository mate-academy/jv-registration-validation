package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with login " + user.getLogin() + " already exists"
            );
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "User login length mustn't be lower than 6 symbols"
            );
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "User password length mustn't be lower than 6 symbols"
            );
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "User age mustn't be lower than 18 years"
            );
        }
        storageDao.add(user);
        return user;
    }
}
