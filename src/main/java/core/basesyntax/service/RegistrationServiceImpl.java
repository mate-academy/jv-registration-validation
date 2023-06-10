package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS_FOR_LOGIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("For registation login is required");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("For registation password is required");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exists. "
                    + "Try another login");
        }
        if (user.getLogin().length() < MIN_CHARACTERS_FOR_LOGIN_PASSWORD) {
            throw new RegistrationException("Login should contain at least 6 characters.");
        }
        if (user.getPassword().length() < MIN_CHARACTERS_FOR_LOGIN_PASSWORD) {
            throw new RegistrationException("Password should contain at least 6 characters.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("For registration its necessary to enter your age");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is "
                    + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
