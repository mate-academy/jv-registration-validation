package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new InvalidUserDataException("Login, password, or age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Not valid age: "
                + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login must be at least 6 characters long");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password must be at least 6 characters long");
        }
        for (User existingUser : Storage.people) {
            if (existingUser.getLogin().equals(user.getLogin())) {
                throw new InvalidUserDataException("There is already a user with such login");
            }
        }

        storageDao.add(user);

        return user;
    }
}
