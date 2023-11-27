package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private static final int MIN_CHARACTERS_IN_LOGIN = 6;
    private static final int MIN_CHARACTERS_IN_PASSWORD = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age is null");
        }
        if (user.getLogin().length() < MIN_CHARACTERS_IN_LOGIN) {
            throw new RegistrationException("Login is too short");
        }
        if (user.getPassword().length() < MIN_CHARACTERS_IN_PASSWORD) {
            throw new RegistrationException("Password is too short");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is "
                    + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
