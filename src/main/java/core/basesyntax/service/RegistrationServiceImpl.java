package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationFailureException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_DATA_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userNullDataValidation(user);
        userLoginAndPasswordValidation(user);
        ageValidation(user);

        String login = user.getLogin();
        if (storageDao.get(login) != null) {
            throw new RegistrationFailureException("This user login exists: " + login);
        }

        storageDao.add(user);
        return storageDao.get(login);
    }

    @Override
    public User getUser(String login) {
        return storageDao.get(login);
    }

    private void userNullDataValidation(User user) {
        if (user == null) {
            throw new RegistrationFailureException("User can't be null! ");
        }

        if (user.getAge() == null) {
            throw new RegistrationFailureException("Age can't be null! ");
        }

        if (user.getLogin() == null) {
            throw new RegistrationFailureException("Login can't be null! ");
        }

        if (user.getPassword() == null) {
            throw new RegistrationFailureException("Password can't be null! ");
        }
    }

    private void userLoginAndPasswordValidation(User user) {
        if (user.getLogin().length() < MIN_DATA_LENGTH) {
            throw new RegistrationFailureException("Your login is too short!");
        }
        if (user.getPassword().length() < MIN_DATA_LENGTH) {
            throw new RegistrationFailureException("Your password is too short");
        }
    }

    private void ageValidation(User user) { //maybe can use Integer from user
        Integer age = user.getAge();

        if (age < MIN_AGE) {
            throw new RegistrationFailureException("Not valid age: "
                    + age + ". Min allowed age is " + MIN_AGE);
        }
    }
}
