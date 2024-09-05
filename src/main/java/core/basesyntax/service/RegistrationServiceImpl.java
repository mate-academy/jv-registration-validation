package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserNotOkException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        try {
            checkingUser(user);
            storageDao.add(user);
            return storageDao.get(user.getLogin());
        } catch (UserNotOkException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkingUser(User user)
            throws UserNotOkException {
        if (user == null) {
            throw new UserNotOkException("User is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserNotOkException("Login is already taken.");
        }

        if (user.getLogin().length() < MIN_CHARS) {
            throw new UserNotOkException("Login must be at least "
                    + MIN_CHARS + " characters long.");
        }

        if (user.getPassword().length() < MIN_CHARS) {
            throw new UserNotOkException("Password must be at least "
                    + MIN_CHARS + " characters long.");
        }

        if (user.getAge() < MIN_AGE) {
            throw new UserNotOkException("User must be at least "
                    + MIN_AGE + " years old.");
        }

        return true;
    }

}
