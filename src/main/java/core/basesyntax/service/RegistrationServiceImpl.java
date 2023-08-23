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
            throw new InvalidUserDataException("User should not be null!");
        } else if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login length should be at least 6 characters!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with this login already exists!");
        } else if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password length should be at least 6 characters!");
        } else if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User should not be younger than 18 years old!");
        }
        return storageDao.add(user);
    }
}
