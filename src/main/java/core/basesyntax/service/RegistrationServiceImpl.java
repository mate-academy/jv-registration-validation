package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserData("User login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserData("User login must be at least 6 symbols");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserData("User password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserData("User password must be at least 6 symbols");
        }

        if (user.getAge() == null) {
            throw new InvalidUserData("User age can't be null");
        }

        if (user.getAge() < 0) {
            throw new InvalidUserData("User age can't be less than 0");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserData("Age must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExist("User with this login already exist");
        }

        return storageDao.add(user);
    }
}
