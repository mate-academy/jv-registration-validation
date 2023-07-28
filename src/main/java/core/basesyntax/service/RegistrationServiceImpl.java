package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("User login cannot be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new ValidationException("User login cannot be less than "
                    + MIN_LENGTH + " characters");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("User password cannot be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new ValidationException("User password cannot be less than "
                    + MIN_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User age cannot be less than "
                    + MIN_AGE + " years");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("A user with this login is already in the list");
        }
        return storageDao.add(user);
    }
}
