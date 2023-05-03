package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE_VALUE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("User login can't be null!");
        }
        if (isUserExist(user)) {
            throw new ValidationException("User with login " + user.getLogin() + " already exist");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("User password can't be null!");
        }
        if (user.getAge() == null) {
            throw new ValidationException("User age can't be null!");
        }
        if (!checkCorrectLoginLength(user)) {
            throw new ValidationException("Login can't be less then 6 characters");
        }
        if (!checkCorrectPasswordLength(user)) {
            throw new ValidationException("Password can't be less then 6 characters");
        }
        if (!checkCorrectAgeValue(user)) {
            throw new ValidationException("Age should be 18 and more");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    @Override
    public User getUser(String login) {
        if (login == null) {
            throw new ValidationException("Login can't be null!");
        }
        if (storageDao.get(login) == null) {
            throw new ValidationException("User with login " + login + " doesn't exist");
        }
        return storageDao.get(login);
    }

    private boolean isUserExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean checkCorrectLoginLength(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            System.out.println("User " + user.getLogin() + " login is least then 6 characters");
        }
        return user.getLogin().length() >= MIN_LOGIN_LENGTH;
    }

    private boolean checkCorrectPasswordLength(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            System.out.println("User " + user.getLogin() + " password is least then 6 characters");
        }
        return user.getPassword().length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean checkCorrectAgeValue(User user) {
        if (user.getAge() < MIN_AGE_VALUE) {
            System.out.println("User " + user.getLogin() + " age is least then 18 characters");
        }
        return user.getAge() >= MIN_AGE_VALUE;
    }
}
