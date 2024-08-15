package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationFailed;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int CORRECT_LOGIN_PASSWORD_SIZE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationFailed("login, password, age can't be " + null);
        }

        if (user.getLogin().length() < CORRECT_LOGIN_PASSWORD_SIZE) {
            throw new RegistrationFailed("login leght must be bigger than --> "
                    + CORRECT_LOGIN_PASSWORD_SIZE
                    + " symbols");
        }

        if (user.getPassword().length() < CORRECT_LOGIN_PASSWORD_SIZE) {
            throw new RegistrationFailed("password lenght must be bigger than --> "
                    + CORRECT_LOGIN_PASSWORD_SIZE
                    + " symbols");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationFailed("you must be older than --> " + MIN_AGE);
        }
        for (User users: Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new RegistrationFailed("login already exists -->" + user.getLogin());
            }
        }

        return storageDao.add(user);
    }
}
