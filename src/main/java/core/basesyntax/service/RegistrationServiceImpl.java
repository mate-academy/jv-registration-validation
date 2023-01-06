package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserException("User field can't be empty.");
        }
        userLogin(user.getLogin());
        userPassword(user.getPassword());
        userAge(user.getAge());
        userId(user.getId());
        return storageDao.add(user);
    }

    private void userLogin(String login) {
        if (login == null) {
            throw new UserException("Login field is null.");
        }
        if (login.isEmpty()) {
            throw new UserException("Login field can't be empty.");
        }
        if (storageDao.get(login) != null) {
            throw new UserException("Login: " + login
                    + " is already exist, please choose another login.");
        }
    }

    private void userPassword(String password) {
        if (password == null) {
            throw new UserException("Password field is null.");
        }
        if (password.contains(password.toUpperCase())) {
            throw new UserException("The password cannot contain only uppercase letters");
        }
        if (password.contains(password.toLowerCase())) {
            throw new UserException("The password cannot contain only lowercase letters");
        }
        if (password.isEmpty()) {
            throw new UserException("Password field can't be empty.");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new UserException("The password length must be minimum " + MIN_PASSWORD_LENGTH
                    + " characters, and your password length is : " + password.length());
        }
    }

    private void userAge(Integer age) {
        if (age == null) {
            throw new UserException("Age field is null.");
        }
        if (age < MIN_USER_AGE) {
            throw new UserException("To register ,your age must be at least: "
                    + MIN_USER_AGE + " ,instead, your age is: " + age);
        }
    }

    private void userId(Long id) {
        if (id == null) {
            throw new UserException("Id is null.");
        }
    }
}
