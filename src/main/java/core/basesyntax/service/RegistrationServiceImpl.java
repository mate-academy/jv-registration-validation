package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Optional;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException("Login must contain at least "
                    + MIN_LENGTH_LOGIN + " characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Password must contain at least "
                    + MIN_LENGTH_PASSWORD + " characters");
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least " + MIN_AGE + " years old");
        }

        Optional<User> existingUser = storageDao.get(user.getLogin());
        if (existingUser.isPresent()) {
            throw new RegistrationException("Can't register user '" + user.getLogin()
                    + "'. User already exists");
        }

        return storageDao.add(user);
    }
}
