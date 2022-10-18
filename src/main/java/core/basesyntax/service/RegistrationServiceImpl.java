package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isUserDataValid(user)) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean isUserDataValid(User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("User cannot be null");
        }
        checkLogin(user.getLogin());
        checkUserAge(user.getAge());
        checkPassword(user.getPassword());
        return true;
    }

    private void checkPassword(String password) {
        if (Objects.isNull(password) || password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be 6 symbols at least");
        }
    }

    private void checkUserAge(Integer age) {
        if (Objects.isNull(age) || age < MINIMUM_USER_AGE) {
            throw new RuntimeException("User must be adult (18+)");
        }
    }

    private void checkLogin(String login) {
        if (Objects.isNull(login) || login.length() == 0) {
            throw new RuntimeException("Login cannot be empty");
        }
        if (Objects.nonNull(storageDao.get(login))) {
            throw new RuntimeException("User already exists in DB with login " + login);
        }
    }

}
