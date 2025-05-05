package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MINIMAL_LENGTH = 2;
    private static final int PASSWORD_MINIMAL_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
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
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with the same login " + user.getLogin()
                    + " already exists");
        }
        if (user.getLogin().length() < LOGIN_MINIMAL_LENGTH) {
            throw new RuntimeException("Login should be " + LOGIN_MINIMAL_LENGTH
                    + " symbols or more");
        }
        if (user.getPassword().length() < PASSWORD_MINIMAL_LENGTH) {
            throw new RuntimeException("Password should be " + PASSWORD_MINIMAL_LENGTH
                    + " symbols or more");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("Users age can't be less than " + MINIMAL_AGE);
        }
        return storageDao.add(user);
    }
}
