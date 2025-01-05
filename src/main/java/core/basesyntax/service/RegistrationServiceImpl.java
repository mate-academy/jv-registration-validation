package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegisterFailedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegisterFailedException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters.");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterFailedException("User with login"
                    + user.getLogin() + "already exists");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegisterFailedException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegisterFailedException("Used must be at least "
                    + MIN_AGE + " years old.");
        }

        storageDao.add(user);
        return user;
    }
}
