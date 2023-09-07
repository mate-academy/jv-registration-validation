package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int MIN_AGE = 18;
    private final int MIN_LOGIN_SIZE = 6;
    private final int MIN_PASSW_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new UserValidationException("User can not be null");
        }
        if (user.getLogin() == null) {
            throw new UserValidationException("login can not be null");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password can not be null");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age can not be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_SIZE) {
            throw new UserValidationException("this login size"
                    + user.getLogin().length() + " must be greater than " + MIN_LOGIN_SIZE);
        }
        if (user.getPassword().length() < MIN_PASSW_SIZE) {
            throw new UserValidationException("this password size"
                    + user.getPassword().length() + " must be greater than " + MIN_PASSW_SIZE);
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("Your age must be greater than " + MIN_AGE);
        }

        if (Storage.people.contains(user)) {
            throw new UserValidationException("this login "
                    + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}