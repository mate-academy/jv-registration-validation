package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 150;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkUserLogin(user);
        checkUserAge(user);
        checkUserPassword(user);
        checkUserExist(user);

        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new UserValidationException("Invalid User data: user is null");
        }
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new UserValidationException(
              "Invalid User login. Login should not be empty. Actual login value is: "
                + user.getLogin()
            );
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE || user.getAge() > MAX_USER_AGE) {
            throw new UserValidationException(
              "Invalid User age. Age should be equal or more than "
                + MIN_USER_AGE
                + ". Actual age value is: "
                + user.getAge()
            );
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserValidationException(
              "Invalid password. Password length should be equal or more than "
                + MIN_PASSWORD_LENGTH
                + ". Actual password length is: "
                + (user.getPassword() == null ? null : user.getPassword().length())
            );
        }
    }

    private void checkUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException(
              "User with the same login already exist. Login value is: " + user.getLogin()
            );
        }
    }

}
