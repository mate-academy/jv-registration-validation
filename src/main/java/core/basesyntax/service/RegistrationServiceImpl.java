package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 1;
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 150;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("Invalid User data: user is null");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserValidationException(
              "Invalid User login. Login should not be empty. Actual login value is: "
                + user.getLogin()
            );
        }

        if (user.getAge() == null || user.getAge() < MIN_USER_AGE || user.getAge() > MAX_USER_AGE) {
            throw new UserValidationException(
              "Invalid User age. Age should be equal or more than "
                + MIN_USER_AGE
                + ". Actual age value is: "
                + user.getAge()
            );
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserValidationException(
              "Invalid password. Password length should be equal or more than "
                + MIN_PASSWORD_LENGTH
                + ". Actual password is: "
                + user.getPassword()
            );
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException(
              "User with the same login already exist. Login value is: " + user.getLogin()
            );

        }

        return storageDao.add(user);
    }
}
