package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exc.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User is 'null'");
        }
        if (user.getAge() == null 
                || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age equals 'null' or is less than " + MIN_AGE);
        }
        if (user.getLogin() == null 
                || user.getLogin().isEmpty()) {
            throw new InvalidDataException("Login equals 'null' or is 0 characters long");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password equals 'null' or is less than " 
                    + MIN_PASSWORD_LENGTH + " characters long");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This login already exists");
        }
    }  
}
