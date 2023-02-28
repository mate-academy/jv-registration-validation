package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with this login already added.");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidUserDataException("Invalid login");
        }
        if (user.getId() == null) {
            throw new InvalidUserDataException("Invalid user Id");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Invalid user age");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Invalid password");
        }
        return storageDao.add(user);
    }
}
