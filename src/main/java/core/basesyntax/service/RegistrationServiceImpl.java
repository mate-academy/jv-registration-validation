package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        return storageDao.add(user);
    }

    private void loginValidation(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("There is user with such login yet");
        }
    }

    private void passwordValidation(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
    } 
      
    private void ageValidation(Integer age) {
        if (age == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (age < MIN_USER_AGE) {
            throw new RegistrationException("User should be at least "
                    + MIN_PASSWORD_LENGTH + " years old");
        }
    }
}
