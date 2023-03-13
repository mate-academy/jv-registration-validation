package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_USER_AGE = 18;
    private final static int MIN_LOGIN_LENGTH = 6;
    private final static int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    private void userValidation(User user) {
        if (user == null) {
            throw  new InvalidUserException(" Wrong user - null");
        }
        loginValidation(user);
        passwordValidation(user);
        ageValidation(user);
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null || storageDao.get(user.getLogin()) != null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("Wrong password - " + user.getPassword());
        }
    }

    private void ageValidation(User user) {
        if (user.getAge() == null || storageDao.get(user.getLogin()) != null
                || user.getAge() < MIN_USER_AGE) {
            throw new InvalidUserException("Wrong age - " + user.getAge());
        }
    }


    private void loginValidation(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null
                || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException("Wrong login - " + user.getLogin());
        }
    }

    @Override
    public User register(User user) {
        userValidation(user);
        return storageDao.add(user);
    }
}
