package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_PASSWORD_LENGTH = 9;
    private static final int VALID_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().equals("")
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Users login field cannot be empty "
                    + "(or login already exist)");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RuntimeException("Users password field cannot be empty "
                    + "(or password less then "
                    + VALID_PASSWORD_LENGTH
                    + "symbols)");
        }
        if (user.getAge() == null || user.getAge() < VALID_USER_AGE) {
            throw new RuntimeException("Users age must be more 18 years "
                    + "(or age field cannot be empty)");
        }
        return storageDao.add(user);
    }
}
