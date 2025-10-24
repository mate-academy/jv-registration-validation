package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (nullCheck(user)
                && criteriaCheck(user)
                && storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        throw new UserAlreadyInSystemException("User already registered");
    }

    public boolean nullCheck(User user) {
        if (user.getLogin() == null) {
            throw new NullLoginException("Received user`s login is null");
        }
        if (user.getPassword() == null) {
            throw new NullPasswordException("Received user`s password is null");
        }
        if (user.getAge() == null) {
            throw new NullAgeException("Received user`s age is null");
        }
        return true;
    }

    public boolean criteriaCheck(User user) {
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new ShortLoginException("Login is too short");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new ShortPasswordException("Password is too short");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UnderAgeUserException("User is under age");
        }
        return true;
    }

    public static class NullLoginException extends RuntimeException {
        public NullLoginException(String message) {
            super(message);
        }
    }

    public static class NullPasswordException extends RuntimeException {
        public NullPasswordException(String message) {
            super(message);
        }
    }

    public static class NullAgeException extends RuntimeException {
        public NullAgeException(String message) {
            super(message);
        }
    }

    public static class ShortLoginException extends RuntimeException {
        public ShortLoginException(String message) {
            super(message);
        }
    }

    public static class ShortPasswordException extends RuntimeException {
        public ShortPasswordException(String message) {
            super(message);
        }
    }

    public static class UnderAgeUserException extends RuntimeException {
        public UnderAgeUserException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyInSystemException extends RuntimeException {
        public UserAlreadyInSystemException(String message) {
            super(message);
        }
    }

}
