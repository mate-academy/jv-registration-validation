package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ZERO_AGE = 0;
    private static final int REQUIRED_AGE = 18;
    private static final int REQUIRED_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("Can't register null user. Please, enter your data");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with such login is already exist");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < REQUIRED_PASSWORD_LENGTH) {
            throw new InvalidDataException("Your password should consist at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < ZERO_AGE) {
            throw new InvalidDataException("Not valid age!");
        }
        if (user.getAge() < REQUIRED_AGE) {
            throw new InvalidDataException("Your age is less then require");
        }
        return storageDao.add(user);
    }
}
