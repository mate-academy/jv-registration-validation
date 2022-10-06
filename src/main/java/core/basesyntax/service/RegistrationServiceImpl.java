package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Not valid password");
        }
        User userByLogin = storageDao.get(user.getLogin());
        if (userByLogin != null) {
            if (userByLogin.equals(user)) {
                throw new RuntimeException("User " + user.toString()
                        + " already exists in the storage");
            } else {
                throw new RuntimeException("Login " + user.getLogin()
                        + " is already taken by another user");
            }
        }
        return storageDao.add(user);
    }
}
