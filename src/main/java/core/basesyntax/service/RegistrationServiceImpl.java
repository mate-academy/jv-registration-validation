package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserAlreadyExistException;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_PASSWORD_LENGTH = 6;
<<<<<<< HEAD
    private static final int VALID_LOGIN_LENGTH = 6;
=======
>>>>>>> 27b63ef850ab35eb31dad3148cf25dedc66b1940
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isUserNull(user);
        isValidUserLogin(user);
        isValidUserPassword(user);
        isValidUserAge(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistException("User: " + user + ", already exist in storage");
        }
        storageDao.add(user);
        return user;
    }

    private void isUserNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }
<<<<<<< HEAD
    }

    private void isValidUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("User`s login can`t be null");
        }
        if (user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new UserRegistrationException("Login have to be longer then 6 character but was: "
                    + user.getLogin().length());
        }
    }

    private void isValidUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new UserRegistrationException("User`s login can`t be null");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password have to be longer then 6 character, "
                    + "but was: " + user.getPassword().length());
        }
    }

    private void isValidUserAge(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("User`s age can`t be null");
        }
        if (user.getAge() < VALID_AGE) {
            throw new UserRegistrationException("Age have to be 18 or older, but was"
=======
        if (user.getLogin() == null || user.getLogin().length() < VALID_PASSWORD_LENGTH) {
            throw new NotValidDataException("Login have to be longer then 6 character but was: "
                    + user.getLogin().length());
        }
        if (user.getPassword() == null || user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new NotValidDataException("Password have to be longer then 6 character but was: "
                    + user.getPassword().length());
        }
        if (user.getAge() < VALID_AGE) {
            throw new NotValidDataException("Age have to be 18 or older, but was"
>>>>>>> 27b63ef850ab35eb31dad3148cf25dedc66b1940
                    + user.getAge());
        }
    }
}
