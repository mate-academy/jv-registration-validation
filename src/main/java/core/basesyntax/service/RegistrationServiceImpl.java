package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AMOUNT_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        checkCopy(user);
        return storageDao.add(user);
    }

    private boolean checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegisterException("User's login cant be null!");
        } else if (user.getLogin().length() < MIN_AMOUNT_CHARACTERS) {
            throw new RegisterException("User's login length = " + user.getLogin().length()
                    + ", but min length = " + MIN_AMOUNT_CHARACTERS);
        }
        return true;
    }

    private boolean checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegisterException("User's age cant be null!");
        } else if (user.getAge() < MIN_AGE) {
            throw new RegisterException("User's age = " + user.getAge()
                    + ", but allowed " + MIN_AGE);
        }
        return true;
    }

    private boolean checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegisterException("User's password cant be null!");
        } else if (user.getPassword().length() < MIN_AMOUNT_CHARACTERS) {
            throw new RegisterException("User's password length = " + user.getPassword().length()
                    + ", but allowed " + MIN_AMOUNT_CHARACTERS);
        }
        return true;
    }

    private boolean checkCopy(User user) {
        for (User users:Storage.people) {
            if (users.equals(user)) {
                throw new RegisterException("User already registered!");
            }
        }
        return true;
    }
}
