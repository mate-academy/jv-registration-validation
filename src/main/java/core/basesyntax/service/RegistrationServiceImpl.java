package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_USER_LOGIN_LENGTH = 6;
    public static final int MIN_USER_PASSWORD_LENGTH = 6;
    public static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();
    
    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        
        if (user.getAge() == null) {
            throw new InvalidUserDataException("Age can't be null");
        }
        
        if (user.getLogin().length() < MIN_USER_LOGIN_LENGTH) {
            throw new InvalidUserDataException("The login is too short, min length is "
                    + MIN_USER_LOGIN_LENGTH);
        }
        
        if (user.getPassword().length() < MIN_USER_PASSWORD_LENGTH) {
            throw new InvalidUserDataException(
                    "The password is too short, min password length is - "
                    + MIN_USER_PASSWORD_LENGTH);
        }
        
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidUserDataException("The minimum age is " + MIN_USER_AGE);
        }
        
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("The user with this login already exists");
        }
        
        return storageDao.add(user);
    }
}
