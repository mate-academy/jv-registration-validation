package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        User addNewUser = storageDao.add(user);

        return addNewUser;
    }

    public void validateUser(User user) {
        checkIsLoginAlreadyRegistered(user.getLogin());
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
    }

    public void checkAge(User user) {
        Integer age = user.getAge();
        if (age == null || age <= MIN_AGE) {
            throw new RegistrationException("You are to young for that)");
        }
    }

    public void checkPassword(User user) {
        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Your password cant be null");
        }
        if (password.length() <= MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Your password to small" + user.getAge()
                    + ".Min allowed length of password is "
                    + MIN_PASSWORD_LENGTH);
        }
    }

    public void checkLogin(User user) {
        String login = user.getLogin();
        if (login == null || login.length() <= MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Your login to small");
        }
    }

    public void checkIsLoginAlreadyRegistered(String login) {
        User checkRegister = storageDao.get(login);
        if (checkRegister != null) {
            throw new RegistrationException("Your login already registered");
        }
    }
}


