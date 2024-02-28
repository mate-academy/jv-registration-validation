package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserAddException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_DEMANDING_LENGTH = 6;
    private static final int MINIMUM_DEMANDING_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            validationUserData(user);
            checkIfUserExist(user.getLogin());
            return storageDao.add(user);
        }
        throw new UserAddException("There is no user to add , because user is null");
    }

    private boolean checkIfUserExist(String login) {
        User foundedUser = storageDao.get(login);
        if (foundedUser != null) {
            if (foundedUser.getLogin()
                    .equals(login)) {
                throw new UserAddException("User "
                        + login + " has already been added");
            }
        }
        return false;
    }

    private void validationUserData(User user) {
        checkLoginLength(user.getLogin());
        checkPasswordLength(user.getPassword());
        checkUserAge(user.getAge());
    }

    private boolean checkUserAge(Integer age) {
        if (age == null || age < 0
                || age < MINIMUM_DEMANDING_AGE) {
            throw new UserAddException("User age  should be more or equals "
                    + MINIMUM_DEMANDING_AGE
                    + " years");
        }
        return true;
    }

    private boolean checkLoginLength(String login) {
        if (login == null || login.length() < MINIMUM_DEMANDING_LENGTH) {
            throw new UserAddException("Length of user login should be more or equals "
                    + MINIMUM_DEMANDING_LENGTH
                    + "symbols");
        }
        return true;
    }

    private boolean checkPasswordLength(String password) {
        if (password == null || password.length() < MINIMUM_DEMANDING_LENGTH) {
            throw new UserAddException("Length of user password should be more or equals "
                    + MINIMUM_DEMANDING_LENGTH
                    + "symbols");
        }
        return true;
    }
}
