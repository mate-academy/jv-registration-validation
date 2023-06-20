package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int ADULT_AGE = 18;
    static final int MIN_LOGIN_LENGTH = 6;
    static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (validateUserData(user)) {
            return storageDao.add(user);
        }
        return null;
    }

    private boolean validateUserData(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Not valid login: " + user.getLogin()
                    + ", min length of login = " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Not valid password: " + user.getPassword()
                    + ", min length of password = " + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < ADULT_AGE) {
            throw new InvalidDataException("Not valid age: " + user.getAge()
                    + ", min age = " + ADULT_AGE);
        }
        if (user.getAge() < 0) {
            throw new InvalidDataException("Not valid age: " + user.getAge()
                    + ", age should be positive");
        }
        if (user.getLogin() != null) {
            User dubledUser = storageDao.get(user.getLogin());
            if (dubledUser != null) {
                throw new InvalidDataException("User with login: " + user.getLogin()
                        + " is already in storage");
            }
        }
        return true;
    }
}
