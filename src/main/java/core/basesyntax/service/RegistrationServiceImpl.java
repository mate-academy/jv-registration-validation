package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserNotValidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_LOGIN_LENGTH = 6;
    static final int MIN_PASSWORD_LENGTH = 6;
    static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new UserNotValidException("User cant be null");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
    }

    private void validateLogin(User user) {
        String userLogin = user.getLogin();

        if (userLogin == null) {
            throw new UserNotValidException("Login of user cant be null");
        }
        if (storageDao.get(userLogin) != null) {
            throw new UserNotValidException(
                    "User with this login exist already. Login is + " + user.getLogin()
            );
        }
        if (userLogin.length() < MIN_LOGIN_LENGTH) {
            throw new UserNotValidException(
                    "Login should be at least 6 symbols. Login is " + userLogin
            );
        }
    }

    private void validatePassword(User user) {
        String userPassword = user.getPassword();

        if (userPassword == null) {
            throw new UserNotValidException("Password cant be null");
        }
        if (userPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new UserNotValidException(
                    "Password should be at least 6 symbols. User password is " + userPassword
            );
        }
    }

    private void validateAge(User user) {
        Integer userAge = user.getAge();

        if (userAge == null) {
            throw new UserNotValidException(
                    "Age of user shouldn't be null."
            );
        }
        if (userAge < MIN_AGE) {
            throw new UserNotValidException(
                    "Age should be at least 18. Age is " + userAge
            );
        }
    }
}
