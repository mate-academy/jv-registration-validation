package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private static final int MIN_CHAR = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_CHAR) {
            throw new RegistrationException("To short password: "
                    + user.getPassword() + ". Min allowed letters in password are " + MIN_CHAR);
        }
        if (user.getLogin().length() < MIN_CHAR) {
            throw new RegistrationException("To short login: "
                    + user.getLogin() + ". Min allowed letters in login are " + MIN_CHAR);
        }
        for (User existingUser : Storage.people) {
            if (existingUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("Login already exists: " + user.getLogin());
            }

        }
        storageDao.add(user);
        return user;
    }

    public User getUser(String login) {
        return storageDao.get(login);
    }
}
