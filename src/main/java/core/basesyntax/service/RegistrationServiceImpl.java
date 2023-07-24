package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int USER_LOGIN_LENGTH = 6;
    public static final int USER_PASSWORD_LENGTH = 6;
    public static final int USER_MIN_ACCESSIBLE_AGE = 18;
    public static final String WHITE_SPACE = " ";

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserRegistrationException("User is null");
        }
        validateUserLogin(user);
        validateUserPassword(user);
        validateUserAge(user);
        return storageDao.add(user);
    }

    private void validateUserAge(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("User age "
                    + user.getAge() + " is not specified. Age can't be null");
        }
        if (user.getAge() < USER_MIN_ACCESSIBLE_AGE) {
            throw new UserRegistrationException("User age "
                    + user.getAge()
                    + " less than " + USER_MIN_ACCESSIBLE_AGE + " years old");
        }
    }

    private void validateUserPassword(User user) {
        if (user.getPassword() == null || user.getPassword().equals(WHITE_SPACE)) {
            throw new UserRegistrationException("User password "
                    + user.getPassword() + " is not specified. Password can't be null");
        }
        if (user.getPassword().length() < USER_PASSWORD_LENGTH) {
            throw new UserRegistrationException("User password length "
                    + user.getPassword().length()
                    + "less than " + USER_PASSWORD_LENGTH + " symbols");
        }
    }

    private void validateUserLogin(User user) {
        if (user.getLogin() == null || user.getLogin().equals(WHITE_SPACE)) {
            throw new UserRegistrationException("User login "
                    + user.getLogin() + " is not specified. Login can't be null");
        }
        if (user.getLogin().length() < USER_LOGIN_LENGTH) {
            throw new UserRegistrationException("User login length "
                    + user.getLogin().length()
                    + " less than " + USER_LOGIN_LENGTH + " symbols");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new UserRegistrationException("User by specified login "
                    + user.getLogin() + " already exist");
        }
    }
}

