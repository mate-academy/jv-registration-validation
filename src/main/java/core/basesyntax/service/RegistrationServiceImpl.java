package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUMBER_OF_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (nullCheck(user) && !isUserExist(user)
                && isAgeCorrect(user) && isPasswordCorrect(user)) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Something went wrong");
    }

    private boolean isUserExist(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            return false;
        }
        throw new RuntimeException("User with login" + user.getLogin() + " exists");
    }

    private boolean nullCheck(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }
        return true;
    }

    private boolean isAgeCorrect(User user) {
        if (user.getAge() >= MIN_AGE) {
            return true;
        }
        throw new RuntimeException("Age is less than 18");
    }

    private boolean isPasswordCorrect(User user) {
        if (user.getPassword().length() >= MIN_NUMBER_OF_CHARACTERS) {
            return true;
        }
        throw new RuntimeException("Password length is less than 6 characters");
    }
}
