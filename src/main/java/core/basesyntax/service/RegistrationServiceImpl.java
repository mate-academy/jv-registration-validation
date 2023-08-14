package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_CHARS = 6;
    private static final int MIN_PASSWORD_CHARS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can`t be null!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can`t be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("There is already such a user in the storage!");
        }
        if (user.getLogin().length() < MIN_LOGIN_CHARS) {
            throw new InvalidDataException("Login length must be more than 6!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_CHARS) {
            throw new InvalidDataException("Password length must be more than 6!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age must be 18+!");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
