package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_AGE_OF_USERS = 18;

    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateIsUserNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    public void validateIsUserNull(User user) {
        if (user == null) {
            throw new RuntimeException("Can't create user");
        }
    }

    public void validateLogin(User user) {
        if (user.getLogin() == null || user.getLogin().contains(" ")
                || user.getLogin().length() == 0) {
            throw new RuntimeException("Please enter the correct login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Please choose a different username."
                    + "This login is already taken");
        }
    }

    public void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("Please enter the correct password");
        }
    }

    public void validateAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE_OF_USERS) {
            throw new RuntimeException("Sorry, your age is not correct");
        }
    }
}
