package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.NotValidationUser;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NotValidationUser("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new NotValidationUser("User login cannot be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new NotValidationUser("User login cannot be less than six characters");
        }
        if (user.getPassword() == null) {
            throw new NotValidationUser("User password cannot be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new NotValidationUser("User password cannot be less than six characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotValidationUser("User age cannot be less than eighteen years");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidationUser("A user with this login is already in the list");
        }
        return storageDao.add(user);
    }
}
