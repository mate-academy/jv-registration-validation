package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNotNull(user);
        checkLogin(user.getLogin().toLowerCase());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    private boolean checkNotNull(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new InvalidUserInputException("User params can't be null");
        }
        return true;
    }

    private boolean checkLogin(String login) {
        if (login.length() < 4 || login.length() > 16
                || !login.matches("\\w++")) {
            throw new InvalidUserInputException("Invalid login");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserInputException("User already exists");
        }
        return true;
    }

    private boolean checkPassword(String password) {
        if (password.length() < 6 || password.length() > 16) {
            throw new InvalidUserInputException("Invalid password");
        }
        return true;
    }

    private boolean checkAge(Integer age) {
        if (age < 18 || age > 140) {
            throw new InvalidUserInputException("Invalid age");
        }
        return true;
    }
}
