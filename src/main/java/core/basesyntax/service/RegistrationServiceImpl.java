package core.basesyntax.service;

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
            throw new IncorrectDataForRegistrationException("User cannot be null.");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new IncorrectDataForRegistrationException("Login is invalid");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IncorrectDataForRegistrationException("Password is invalid");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new IncorrectDataForRegistrationException("Age is invalid");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new IncorrectDataForRegistrationException("Login already exists");
        }
        return storageDao.add(user);
    }
}

