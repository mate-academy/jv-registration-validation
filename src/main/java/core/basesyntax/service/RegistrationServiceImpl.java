package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGHT = 6;
    private static final int USER_MIN_AGE = 18;
    private StorageDao storageDao;

    @Override
    public User register(User user) {
        storageDao = new StorageDaoImpl();
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        if (user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new ValidationException("Users parameter cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with the specified login is already exist ");
        }
        if (user.getLogin().length() < MIN_LENGHT || user.getPassword().length() < MIN_LENGHT) {
            throw new ValidationException("User login and password "
                    + "must have at least 6 characters");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new ValidationException("Registration is available only from the age of 18");
        }
        storageDao.add(user);
        return user;
    }
}

