package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyUserForNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        verifyUserExist(user);
        return storageDao.add(user);
    }

    private void verifyUserForNull(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User is null");
        }
    }

    private void validateLogin(User user) {
        String login = user.getLogin();
        if (Objects.isNull(login)) {
            throw new InvalidUserDataException("User login is null");
        }
        if (!(login.contains("@"))) {
            throw new InvalidUserDataException("User login is not email");
        }
        if (login.substring(0, login.indexOf("@")).length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login length is less than 6 characters "
                    + "(up to the @ symbol)");
        }
    }

    private void validatePassword(User user) {
        String password = user.getPassword();
        if (Objects.isNull(password)) {
            throw new InvalidUserDataException("User password is null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password length is less than 6 characters");
        }
    }

    private void validateAge(User user) {
        Integer age = user.getAge();
        if (Objects.isNull(age) || age < MIN_AGE) {
            throw new InvalidUserDataException("Not valid age: " + age
                    + ". Min allowed age is " + MIN_AGE);
        }
    }

    private void verifyUserExist(User user) {
        if (!Objects.isNull(storageDao.get(user.getLogin()))) {
            throw new InvalidUserDataException(" User already exist");
        }
    }
}
