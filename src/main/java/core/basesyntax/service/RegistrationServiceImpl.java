package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        if (user.getAge() == null) {
            throw new InvalidDataException("Missing information: User age");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Missing information: User login");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Missing information: User Password");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("this login already exists");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidDataException("Login is too short");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password is too short");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("You have to be at least 18 years old to register");
        }
        storageDao.add(user);
        return user;
    }
}
