package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_CHARS = 6;
    private static final int MIN_PASS_CHARS = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String login = user.getLogin();
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new UserRegistrationException("User with login " + login + " already exists.");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_CHARS) {
            throw new UserRegistrationException("Login must have more than 6 characters!");
        }
        if (user.getPassword().length() < MIN_PASS_CHARS) {
            throw new UserRegistrationException("Password must have more than 6 characters!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("Sorry, but your age not exist our rules");
        }
        return storageDao.add(user);
    }
}
