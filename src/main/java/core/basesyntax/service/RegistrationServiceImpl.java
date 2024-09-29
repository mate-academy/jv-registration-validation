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
    private final StorageDao storageDao = new StorageDaoImpl();
    private Set<String> loginSet = new HashSet<>();

    private void loginSetInit() {
        for (User user : Storage.people) {
            loginSet.add(user.getLogin());
        }
    }
    @Override
    public User register(User user) throws UserNotAllowed {
        if (user != null) {
            loginSetInit();
            if (loginSet != null && loginSet.contains(user.getLogin())) {
                throw new UserNotAllowed("User with login "
                        + user.getLogin() + " already exist");
            }
            if (user.getLogin() == null || user.getLogin().length()
                    < MINIMAL_LOGIN_LENGTH) {
                throw new UserNotAllowed("Login must be at least "
                        + MINIMAL_LOGIN_LENGTH + " characters long");
            } else if (user.getPassword() == null || user.getPassword().length()
                    < MINIMAL_PASSWORD_LENGTH) {
                throw new UserNotAllowed("Password must be at least "
                        + MINIMAL_PASSWORD_LENGTH + " characters long");
            } else if (user.getAge() == null || user.getAge() < MINIMAL_USER_AGE) {
                throw new UserNotAllowed("User must be at least "
                        + MINIMAL_PASSWORD_LENGTH + " years old");
            }
            Storage.people.add(user);
            return user;
        }
        throw new UserNotAllowed("User can not be null");
    }
}
