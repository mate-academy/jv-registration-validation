package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMAL_PASSWORD_LENGTH = 6;
    public static final int MINIMAL_AGE_REQUIREMENT = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User == null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Duplicate login " + user.getLogin());
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should be longer then "
                                        + MINIMAL_PASSWORD_LENGTH + " symbols");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE_REQUIREMENT) {
            throw new RuntimeException("You should be older then" + MINIMAL_AGE_REQUIREMENT);
        }

        storageDao.add(user);
        return user;
    }
}
