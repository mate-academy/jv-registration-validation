package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.UserExistsException;
import core.basesyntax.service.exception.UserValidationException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("Invalid age");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserExistsException("User with this login already exists: "
                    + user.getLogin());
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserValidationException("Password should be longer than "
                    + MIN_PASSWORD_LENGTH + " chars");
        }
        return storageDao.add(user);
    }
}
