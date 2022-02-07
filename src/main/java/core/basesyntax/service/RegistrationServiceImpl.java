package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRE_PASSWORD_MIN_LENGTH = 6;
    private static final int REQUIRE_MIN_AGE = 18;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkPassword(String password) {
        if (password.length() < REQUIRE_PASSWORD_MIN_LENGTH) {
            throw new RuntimeException(
                    "Sorry, your password is too small." + password.length());
        }
    }

    private void checkAge(int age) {
        if (age < REQUIRE_MIN_AGE) {
            throw new RuntimeException("Sorry, age should be the same 18 y. old.");
        }
    }

    private void checkLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new RuntimeException("Sorry, this login already exit");
        }
    }
}
