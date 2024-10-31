package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidUserAgeException;
import core.basesyntax.exeptions.InvalidUserDataException;
import core.basesyntax.exeptions.InvalidUserLoginException;
import core.basesyntax.exeptions.InvalidUserPasswordException;
import core.basesyntax.exeptions.NullUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullUserException();
        } else if (isUserValid(user)) {
            storageDao.add(user);
            return user;
        } else {
            return null;
        }
    }

    private boolean isUserValid(User user) {
        try {
            return isUserLoginValid(user.getLogin())
                    && isUserPasswordValid(user.getPassword())
                    && isUserAgeValid(user.getAge());
        } catch (InvalidUserLoginException
                 | InvalidUserPasswordException
                 | InvalidUserAgeException e) {
            throw new InvalidUserDataException();
        }
    }

    private boolean isUserAgeValid(Integer age) throws InvalidUserAgeException {
        if (age >= MIN_AGE) {
            return true;
        } else {
            throw new InvalidUserAgeException();
        }
    }

    private boolean isUserPasswordValid(String password) throws InvalidUserPasswordException {
        if (password.length() >= MIN_PASSWORD_LENGTH) {
            return true;
        } else {
            throw new InvalidUserPasswordException();
        }
    }

    private boolean isUserLoginValid(String login) throws InvalidUserLoginException {
        if (login.length() >= MIN_LOGIN_LENGTH && storageDao.get(login) == null) {
            return true;
        } else {
            throw new InvalidUserLoginException();
        }
    }

}
