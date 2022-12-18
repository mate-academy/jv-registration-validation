package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exc.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkValidity(user);
        storageDao.add(user);
        return user;
    }

    private void checkValidity(User user) {
        if (user == null) {
            throw new InvalidDataException("User is 'null'");
        }
        if (user.getAge() == null 
                || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age equals 'null' or is less than 18");
        }
        if (user.getLogin() == null 
                || user.getLogin().equals("")) {
            throw new InvalidDataException("Login equals 'null' or is 0 characters long");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < 6) {
            throw new InvalidDataException(
                    "Password equals 'null' or is less than 6 characters long");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This login already exists");
        }
    }
    
}
