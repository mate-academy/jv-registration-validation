package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationFailureException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_DATA_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userDataValidation(user);

        String login = user.getLogin();
        if (storageDao.get(login) != null) {
            throw new RegistrationFailureException("This user login exists: " + login);
        }

        return storageDao.add(user);
    }

    private void userDataValidation(User user) {
        userNullValidation(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void userNullValidation(User user) {
        if (user == null) {
            throw new RegistrationFailureException("User can't be null! ");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationFailureException("Login can't be null! ");
        }
        if (login.length() < MIN_DATA_LENGTH) {
            throw new RegistrationFailureException("Your login is too short!");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationFailureException("Password can't be null! ");
        }
        if (password.length() < MIN_DATA_LENGTH) {
            throw new RegistrationFailureException("Your password is too short");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationFailureException("Age can't be null! ");
        }
        if (age < MIN_AGE) {
            throw new RegistrationFailureException("Not valid age: "
                    + age + ". Min allowed age is " + MIN_AGE);
        }
    }
}
