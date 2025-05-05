package core.basesyntax.service;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 26;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 90;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationValidationException("User field can't be empty!");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationValidationException("Login field is empty");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationValidationException("This login " + login + " already exists");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationValidationException("Password field is empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationValidationException(
                    "Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new RegistrationValidationException(
                    "Password can't be more " + MAX_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationValidationException("Age field is empty");
        }
        if (age < MIN_AGE) {
            throw new RegistrationValidationException(
                    "Your age is less than allowable" + age);
        }
        if (age > MAX_AGE) {
            throw new RegistrationValidationException(
                    "Your age is over than maximum allowable" + age);
        }
    }
}
