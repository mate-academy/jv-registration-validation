package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("Input User is null");
        }
        loginValidator(user);
        passwordValidator(user);
        ageValidator(user);
        return storageDao.add(user);
    }

    private void ageValidator(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is "
                    + MIN_AGE);
        }
        if (user.getAge() == null) {
            throw new ValidationException("User age is null");
        }
    }
    private void passwordValidator(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }
        if (user.getPassword().length() < DEFAULT_MIN_CHARACTERS) {
            throw new ValidationException("Min allowed password length is "
                    + DEFAULT_MIN_CHARACTERS);
        }
    }

    private void loginValidator(User user) {
        User userInStorage = storageDao.get(user.getLogin());
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null");
        }
        if (user.getLogin().length() < DEFAULT_MIN_CHARACTERS) {
            throw new ValidationException("Min allowed login length is "
                    + DEFAULT_MIN_CHARACTERS);
        }
        if (!(userInStorage == null)
                && user.getLogin().equals(userInStorage.getLogin())) {
            throw new ValidationException("Same login was already registered, please try again");
        }

    }
}
