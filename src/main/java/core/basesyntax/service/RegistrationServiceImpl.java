package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exseption.InvalidAgeException;
import core.basesyntax.exseption.InvalidLoginException;
import core.basesyntax.exseption.InvalidPasswordException;
import core.basesyntax.exseption.LoginIsTakenException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_NUMBER_OF_SYMBOLS = 6;
    private static StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidLoginException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidPasswordException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidAgeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_SYMBOLS) {
            throw new InvalidPasswordException("Password can't be null");
        }
        if (user.getAge() < 18) {
            throw new InvalidAgeException("Invalid age");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginIsTakenException("This login already exists");
        }
        return storageDao.add(user);
    }
}
