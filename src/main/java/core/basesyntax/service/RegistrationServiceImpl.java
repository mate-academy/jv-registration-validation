package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exc.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String USER_NULL_MSG = 
            new String("User is 'null'");
    private static final String USER_AGE_INVALID_MSG = 
            new String("Age equals 'null' or is less than 18");
    private static final String USER_LOGIN_INVALID_MSG = 
            new String("Login equals 'null' or is 0 characters long");
    private static final String USER_PASSWORD_INVALID_MSG = 
            new String("Password equals 'null' or is less than 6 characters long");
    private static final String USER_LOGIN_EXISTS_MSG = 
            new String("This login already exists");

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidDataException(USER_NULL_MSG);
        }
        if (user.getAge() == null 
                || user.getAge() < MIN_AGE) {
            throw new InvalidDataException(USER_AGE_INVALID_MSG);
        }
        if (user.getLogin() == null 
                || user.getLogin().isEmpty()) {
            throw new InvalidDataException(USER_LOGIN_INVALID_MSG);
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException(USER_PASSWORD_INVALID_MSG);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException(USER_LOGIN_EXISTS_MSG);
        }
    }  
}
