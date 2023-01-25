package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_USERS_AGE = 18;
    public static final int MINIMUM_PASS_CHARS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null || user.getPassword() == null || user.getAge() == null
                || user.getLogin().length() == 0) {
            throw new RegistrationException("All of the user`s fields must be filled",
                    new Throwable());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with such login: \""
                    + user.getLogin()
                    + "\" already exists",
                    new Throwable());
        }
        if (user.getAge() < MINIMUM_USERS_AGE) {
            throw new RegistrationException("The user with: "
                    + user.getAge()
                    + " years old - not allowed",
                    new Throwable());
        }
        if (user.getPassword().length() < MINIMUM_PASS_CHARS) {
            throw new RegistrationException("The password must be not less than: "
                    + MINIMUM_PASS_CHARS
                    + " chars",
                    new Throwable());
        }
        storageDao.add(user);
        return user;
    }
}
