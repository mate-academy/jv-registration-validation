package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserValidationException {
        if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserValidationException("Login is shorter than six characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("There is a user with this login in the Storage");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserValidationException("Password is shorter than six characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("User is too young. User must be eighteen or older");
        }
        return storageDao.add(user);
    }

}
