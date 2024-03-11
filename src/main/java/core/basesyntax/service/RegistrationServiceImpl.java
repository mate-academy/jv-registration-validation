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
        checkUserIsNotNull(user);
        checkUserLogin(user);
        checkUserPassword(user);
        checkUserAge(user);
        checkUserIsNotExists(user);
    }

    private static void checkUserIsNotNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }
    }

    private static void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("User login can`t be null");
        }
        if (user.getLogin().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(
                    String.format("User login %s length less then %d characters",
                            user.getLogin(),
                            LOGIN_PASSWORD_MIN_LENGTH)
            );
        }
    }

    private static void checkUserPassword(User user) {
        if (Objects.isNull(user.getPassword())) {
            throw new UserRegistrationException("User password can`t be null");
        }
        if (user.getPassword().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(
                    String.format("User password %s length less then %d characters",
                            user.getPassword(),
                            LOGIN_PASSWORD_MIN_LENGTH)
            );
        }
    }

    private static void checkUserAge(User user) {
        if (Objects.isNull(user.getAge())) {
            throw new UserRegistrationException("User age can`t be null");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new UserRegistrationException(
                    String.format("User age %d less then %d years",
                            user.getAge(),
                            USER_MIN_AGE)
            );
        }
    }

    private void checkUserIsNotExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException(
                    String.format("User with login %s already exists",
                            user.getLogin())
            );
        }
    }
}
