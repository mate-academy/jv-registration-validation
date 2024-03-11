package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIsUserValid(user);
        return storageDao.add(user);
    }

    private void checkIsUserValid(User user) {
        checkUserIsNull(user);
        checkUserLoginIsNull(user);
        checkUserPasswordIsNull(user);
        checkUserLoginLength(user);
        checkUserPasswordLength(user);
        checkUserAge(user);
        checkIsUserAlreadyExists(user);
    }

    private static void checkUserIsNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }
    }

    private static void checkUserLoginIsNull(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("User login can`t be null");
        }
    }

    private static void checkUserPasswordIsNull(User user) {
        if (Objects.isNull(user.getPassword())) {
            throw new UserRegistrationException("User password can`t be null");
        }
    }

    private static void checkUserLoginLength(User user) {
        if (user.getLogin().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(
                    String.format("User login %s length less then %d characters",
                            user.getLogin(),
                            LOGIN_PASSWORD_MIN_LENGTH)
            );
        }
    }

    private static void checkUserPasswordLength(User user) {
        if (user.getPassword().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(
                    String.format("User password %s length less then %d characters",
                            user.getPassword(),
                            LOGIN_PASSWORD_MIN_LENGTH)
            );
        }
    }

    private static void checkUserAge(User user) {
        if (user.getAge() < USER_MIN_AGE) {
            throw new UserRegistrationException(
                    String.format("User age %d less then %d years",
                            user.getAge(),
                            USER_MIN_AGE)
            );
        }
    }

    private void checkIsUserAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException(
                    String.format("User with login %s already exists",
                            user.getLogin())
            );
        }
    }
}
