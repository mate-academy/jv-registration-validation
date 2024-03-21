package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_PASSWORD_LOGIN_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isUserIsNull(user);
        isSomeDataNull(user);
        isUserExist(user);
        isLoginValid(user);
        isPasswordValid(user);
        isUserAgeValid(user);
        storageDao.add(user);
        return user;
    }

    private void isSomeDataNull(User user) {
        StringBuilder message = new StringBuilder();
        message.append("Cannot register user, ");
        int count = 0;
        if (user.getId() == null) {
            message.append("Id ");
            count++;
        }
        if (user.getLogin() == null) {
            message.append("Login ");
            count++;
        }
        if (user.getPassword() == null) {
            message.append("Password ");
            count++;
        }
        if (user.getAge() == null) {
            message.append("Age ");
            count++;
        }
        if (count > 0) {
            message.append("can't be null");
            throw new RegistrationException(message.toString());
        }
    }

    private void isUserIsNull(User user) {
        if (user == null) {
            throw new RegistrationException("Cannot register user, user is null");
        }
    }

    private void isUserAgeValid(User user) {
        if (user.getAge() < VALID_AGE) {
            throw new RegistrationException("Cannot register user with age "
                    + user.getAge()
                    + ", minimal allowed age is "
                    + VALID_AGE);
        }
    }

    private void isPasswordValid(User user) {
        if (user.getPassword().length() < VALID_PASSWORD_LOGIN_LENGTH) {
            throw new RegistrationException("Cannot register user, "
                    + "password is shorter than 6 characters");
        }
    }

    private void isLoginValid(User user) {
        if (user.getLogin().length() < VALID_PASSWORD_LOGIN_LENGTH) {
            throw new RegistrationException("Cannot register user, "
                    + "login is shorter than 6 characters");
        }
    }

    private void isUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Cannot register user, login is occupied");
        }
    }
}
