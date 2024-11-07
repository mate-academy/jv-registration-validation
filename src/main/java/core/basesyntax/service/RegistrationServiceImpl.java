package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;

    public static final int MIN_LOG = 6;

    public static final int MIN_PASS = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserRegistrationException {
        if (user.getAge() == null || user.getPassword() == null || user.getLogin() == null) {
            throw new UserRegistrationException("User login,password or age is null!");
        }
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new UserRegistrationException("User login or password is empty!"
                    + user.getLogin());
        }
        if ((user.getLogin().length()) < MIN_LOG) {
            throw new UserRegistrationException("User login must be at least " + MIN_LOG
                    + " characters long! - "
                    + user.getLogin());
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("The user must not be less than "
                    + MIN_AGE + " years old! - "
                    + user.getAge());
        }
        if ((user.getPassword().length() < MIN_PASS)) {
            throw new UserRegistrationException("User password is need to be at least "
                    + MIN_PASS + " letters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User with this login is already register! - "
                    + user.getLogin());
        }
        return storageDao.add(user);
    }
}
