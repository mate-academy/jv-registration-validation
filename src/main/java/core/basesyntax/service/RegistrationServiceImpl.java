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
        if (checkUserIsNull(user)) {
            throw new RegisterException("Cannot register user, user is null");
        }
        String message = checkSomeDataIsNull(user);
        if (message != null) {
            throw new RegisterException(message);
        }
        if (checkIfLoginOccupied(user)) {
            throw new RegisterException("Cannot register user, login is occupied");
        }
        if (!checkLoginLengthIsValid(user)) {
            throw new RegisterException("Cannot register user, login is shorter than 6 characters");
        }
        if (!checkPasswordLengthIsValid(user)) {
            throw new RegisterException("Cannot register user, "
                    + "password is shorter than 6 characters");
        }
        if (!checkUserAgeIsValid(user)) {
            throw new RegisterException("Cannot register user with age "
                    + user.getAge()
                    + ", minimal allowed age is "
                    + VALID_AGE);
        }
        storageDao.add(user);
        return user;
    }

    private String checkSomeDataIsNull(User user) {
        StringBuilder message = new StringBuilder();
        message.append("Cannot register user, ");
        int count = 0;
        if (checkUserIdIsNull(user)) {
            message.append("Id ");
            count++;
        }
        if (checkUserLoginIsNull(user)) {
            message.append("Login ");
            count++;
        }
        if (checkUserPasswordIsNull(user)) {
            message.append("Password ");
            count++;
        }
        if (checkUserAgeIsNull(user)) {
            message.append("Age ");
            count++;
        }
        if (count > 0) {
            message.append("can't be null");
            return message.toString();
        }
        return null;
    }

    private boolean checkUserIsNull(User user) {
        return user == null;
    }

    private boolean checkUserIdIsNull(User user) {
        return user.getId() == null;
    }

    private boolean checkUserLoginIsNull(User user) {
        return user.getLogin() == null;
    }

    private boolean checkUserPasswordIsNull(User user) {
        return user.getPassword() == null;
    }

    private boolean checkUserAgeIsNull(User user) {
        return user.getAge() == null;
    }

    private boolean checkUserAgeIsValid(User user) {
        return user.getAge() >= VALID_AGE;
    }

    private boolean checkPasswordLengthIsValid(User user) {
        return user.getPassword().length() >= VALID_PASSWORD_LOGIN_LENGTH;
    }

    private boolean checkLoginLengthIsValid(User user) {
        return user.getLogin().length() >= VALID_PASSWORD_LOGIN_LENGTH;
    }

    private boolean checkIfLoginOccupied(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
