package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        User addNewUser = storageDao.add(user);

        return addNewUser;
    }

    public void userValidation(User user) {
        isUserAlreadyRegistered(user.getLogin());
        isAdult(user);
        isPasswordLargeEnough(user);
        isLoginLargeEnough(user);
    }

    public void isAdult(User user) {
        Integer age = user.getAge();
        if (age == null || age <= MIN_AGE) {
            throw new RegistrationError("You are to young for that)");
        }
    }

    public void isPasswordLargeEnough(User user) {
        String password = user.getPassword();
        if (password == null || password.length() <= MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationError("Your password to small");
        }
    }

    public void isLoginLargeEnough(User user) {
        String login = user.getLogin();
        if (login == null || login.length() <= MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationError("Your login to small");
        }
    }

    public void isUserAlreadyRegistered(String login) {
        User checkRegister = storageDao.get(login);
        if (checkRegister != null) {
            throw new RegistrationError("Your login already registered");
        }
    }
}


