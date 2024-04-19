package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SYMBOL = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidInputException("Login can`t be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputException("Password can`t be null");
        }
        if (user.getAge() == null) {
            throw new InvalidInputException("Age can`t be null");
        }
        if (user.getLogin().length() < MIN_SYMBOL) {
            throw new InvalidInputException("Login: " + user.getLogin()
                    + " is invalid. Login must have at least " + MIN_SYMBOL + "symbol");
        }
        if (user.getPassword().length() < MIN_SYMBOL) {
            throw new InvalidInputException("Password: " + user.getPassword()
                    + " is invalid. Password must have at least " + MIN_SYMBOL + "symbol");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputException("Age: " + user.getAge()
                    + " is invalid. User age must have at least " + MIN_AGE);
        }

        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        return null;
    }
}