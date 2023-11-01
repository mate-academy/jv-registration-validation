package core.basesyntax.service;

import core.basesyntax.customexception.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can not be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already exists");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new InvalidDataException("Login can not be less than "
                    + MIN_LENGTH_LOGIN + " characters");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can not be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidDataException("Password can not be less than "
                    + MIN_LENGTH_PASSWORD + " characters");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can not be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidDataException("Age must be over " + MIN_USER_AGE);
        }

        return storageDao.add(user);
    }
}
