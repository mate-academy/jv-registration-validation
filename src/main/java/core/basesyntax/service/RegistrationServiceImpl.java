package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN_VALUE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyPassword(user.getPassword());
        verifyAge(user.getAge());
        verifyLogin(user.getLogin());
        return storageDao.add(user);
    }

    private boolean verifyPassword(String password) {
        if (!checkStringHasWhitespacesOnly(password)) {
            throw new RuntimeException("Password shouldn't contains whitespaces. "
                   + "It should contains characters");
        }
        if (password == null || password.length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password should have 6 and more characters");
        }
        return true;
    }

    private boolean verifyAge(Integer age) {
        if (age < AGE_MIN_VALUE) {
            throw new RuntimeException("User should be at least 18 y.o.");
        }
        return true;
    }

    private boolean verifyLogin(String login) {
        if (!checkStringHasWhitespacesOnly(login)) {
            throw new RuntimeException("Login shouldn't contains whitespaces. "
                    + "It should contains characters");
        }
        if (login != null) {
            if (storageDao.get(login) != null) {
                throw new RuntimeException("User with such login exist already");
            }
        }
        return true;
    }

    private boolean checkStringHasWhitespacesOnly(String str) {
        return str.trim().length() > 0;
    }
}
