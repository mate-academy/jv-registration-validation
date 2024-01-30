package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.equals(new User())) {
            throw new RegistrationException("Fill in data fields for empty user");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException(("Login cannot be null"));
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }

        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASS_LENGTH + " characters");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You must be at least "
                    + MIN_AGE + " years old to register");
        }

        for (User storageUser : Storage.people) {
            if (storageUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with current login already exists");
            }
        }

        return storageDao.add(user);
    }
}
