package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_ALLOWABLE_LENGTH = 6;
    private static final int MIN_ALLOWABLE_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login can`t be null");
        }
        loginValidator(user.getLogin());
        if (user.getPassword() == null) {
            throw new InvalidDataException("User password can`t be null");
        }
        passwordValidator(user.getPassword());
        if (user.getAge() == null) {
            throw new InvalidDataException("User age can`t be null");
        }
        ageValidator(user.getAge());
        userIsExist(user);
        storageDao.add(user);
        return user;
    }

    private void loginValidator(String login) {
        if (login.length() < MIN_ALLOWABLE_LENGTH) {
            throw new InvalidDataException("User login must be at least "
                    + MIN_ALLOWABLE_LENGTH
                    + " characters");
        }
    }

    private void passwordValidator(String password) {
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

    private void userIsExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with login "
                    + user.getLogin()
                    + " already exists");
        }
    }
}
