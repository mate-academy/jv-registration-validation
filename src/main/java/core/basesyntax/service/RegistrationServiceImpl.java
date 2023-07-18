package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserNotValidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        storageDao.add(user);
        return user;
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new UserNotValidException("User cant be null");
        }
        checkLoginIsOk(user);
        checkPasswordIsOk(user);
        checkAge(user);
    }

    private void checkLoginIsOk(User user) {
        String userLogin = user.getLogin();

        if (userLogin == null) {
            throw new UserNotValidException("Login of user cant be null");
        }
        if (storageDao.get(userLogin) != null) {
            throw new UserNotValidException(
                    "User with this login exist already. Login is + " + user.getLogin()
            );
        }
        if (userLogin.length() < 6) {
            throw new UserNotValidException(
                    "Login should be at least 6 symbols. Login is " + userLogin
            );
        }
    }

    private void checkPasswordIsOk(User user) {
        String userPassword = user.getPassword();

        if (userPassword == null) {
            throw new UserNotValidException("Password cant be null");
        }
        if (userPassword.length() < 6) {
            throw new UserNotValidException(
                    "Password should be at least 6 symbols. User password is " + userPassword
            );
        }
    }

    private void checkAge(User user) {
        Integer userAge = user.getAge();

        if (userAge == null) {
            throw new UserNotValidException(
                    "Age of user shouldn't be null."
            );
        }
        if (userAge < 18) {
            throw new UserNotValidException(
                    "Age should be at least 18. Age is " + userAge
            );
        }
    }
}
