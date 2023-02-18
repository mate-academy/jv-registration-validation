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
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new RuntimeException("User login field "
                    + "cannot be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User login "
                    + "already exists");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password field "
                    + "cannot be empty");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RuntimeException("User password less then "
                    + VALID_PASSWORD_LENGTH
                    + " symbols");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age field "
                    + "cannot be empty");
        }
        if (user.getAge() < VALID_USER_AGE) {
            throw new RuntimeException("Users age must be more "
                    + VALID_USER_AGE
                    + " years");
        }
        return storageDao.add(user);
    }
}
