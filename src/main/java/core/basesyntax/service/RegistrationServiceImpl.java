package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().equals("")
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Wrong login of registering user "
                    + "(cannot be empty or already exist)");
        }
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password is incorrect "
                    + "(cannot be empty or less then " + PASSWORD_MIN_LENGTH + " symbols)");
        }
        if (user.getAge() == null || user.getAge() < USER_MIN_AGE) {
            throw new RuntimeException("The registered user must be an adult "
                    + "(or age field cannot be empty)");
        }
        return storageDao.add(user);
    }
}
