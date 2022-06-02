package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULTHOOD_AGE = 18;
    private static final int LENGTH_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNullException("user is null");
        }
        loginValidate(user.getLogin());
        userAgeValidate(user.getAge());
        passwordValidate(user.getPassword());
        return storageDao.add(user);
    }

    private void loginValidate(String login) {
        if (login == null) {
            throw new UserHasNoLoginException("user's login is null");
        }
        if (login.replaceAll("\\W+", "")
                .length() - login.length() != 0) {
            throw new RuntimeException("login has at least one invalid symbol");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("user with such login already exists");
        }
    }

    private void userAgeValidate(int age) {
        if (age > -1 && age < ADULTHOOD_AGE) {
            throw new RuntimeException("user must first grow up a little; age less than 18");
        }
        if (age < 0) {
            throw new RuntimeException("negative user's age");
        }
    }

    private void passwordValidate(String password) {
        if (password == null) {
            throw new UserPasswordNullException("user's password is null");
        }
        if (password.length() < LENGTH_OF_PASSWORD) {
            throw new RuntimeException("password too short, must be at least six symbols");
        }
        if (password.replaceAll("\\W+", "")
                .length() - password.length() != 0) {
            throw new RuntimeException("password has at least one invalid symbol");
        }
    }
}
