package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ALLOWABLE_LENGTH = 6;
    private static final int MIN_ALLOWABLE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginValidator(user);
        passwordValidator(user.getPassword());
        ageValidator(user.getAge());
        return storageDao.add(user);
    }

    private void loginValidator(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login can`t be null");
        }
        if (user.getLogin().length() < MIN_ALLOWABLE_LENGTH) {
            throw new InvalidDataException("User login must be at least "
                    + MIN_ALLOWABLE_LENGTH
                    + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with login "
                    + user.getLogin()
                    + " already exists");
        }
    }

    private void passwordValidator(String password) {
        if (password == null) {
            throw new InvalidDataException("User password can`t be null");
        }
        if (password.length() < MIN_ALLOWABLE_LENGTH) {
            throw new InvalidDataException("User password must be at least "
                    + MIN_ALLOWABLE_LENGTH
                    + " characters");
        }
    }

    private void ageValidator(int age) {
        if (age <= 0) {
            throw new InvalidDataException("User age cannot be less than 0");
        }
        if (age < MIN_ALLOWABLE_AGE) {
            throw new InvalidDataException("User must be at least "
                    + MIN_ALLOWABLE_AGE
                    + " years old or older");
        }
    }
}
