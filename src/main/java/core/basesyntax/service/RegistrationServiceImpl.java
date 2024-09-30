package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserNotAllowed;
import core.basesyntax.model.User;
import java.util.HashSet;
import java.util.Set;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_USER_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao;
    private Set<String> loginSet;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
        loginSet = new HashSet<>();
        for (User user : Storage.people) {
            loginSet.add(user.getLogin());
        }
    }

    @Override
    public User register(User user) throws UserNotAllowed {
        validateUser(user);
        storageDao.add(user);
        loginSet.add(user.getLogin());
        return user;
    }

    private void validateUser(User user) throws UserNotAllowed {
        if (user == null) {
            throw new UserNotAllowed("User can not be null");
        }
        if (loginSet.contains(user.getLogin())) {
            throw new UserNotAllowed("User with login " + user.getLogin() + " already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new UserNotAllowed("Login cannot be null or less than "
                    + MINIMAL_LOGIN_LENGTH + " characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new UserNotAllowed("Password must be at least "
                    + MINIMAL_PASSWORD_LENGTH + " characters long");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_USER_AGE) {
            throw new UserNotAllowed("User must be at least " + MINIMAL_USER_AGE + " years old");
        }
    }
}
