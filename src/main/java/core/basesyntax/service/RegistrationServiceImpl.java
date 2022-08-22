package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already exist " + user.getLogin());
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be longer then "
                    + MIN_PASSWORD_LENGTH + " character");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Your must be older then "
                    + MIN_USER_AGE + " years old");
        }
        return storageDao.add(user);
    }
}
