package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        isLoginFreeValidation(user.getLogin());
        return storageDao.add(user);
    }

    private boolean loginValidation(String userLogin) {
        if (userLogin == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (userLogin.length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        return true;
    }

    private boolean passwordValidation(String userPassword) {
        if (userPassword == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (userPassword.length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        return true;
    }

    private boolean ageValidation(int userAge) {
        if (userAge < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + userAge
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (userAge > MAX_AGE) {
            throw new RegistrationException("Not valid age: " + userAge
                    + ". Max allowed age is " + MAX_AGE);
        }
        return true;
    }

    private boolean isLoginFreeValidation(String userLogin) {
        if (storageDao.get(userLogin) != null) {
            throw new RegistrationException("This login is already taken");
        }
        return true;
    }
}
