package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new UserRegistrationException("Please enter your data for registration!");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Please enter your login!");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Your login must contain more than "
                    + MIN_LENGTH + " characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("A user with this login already exists!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Please enter your password!");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Your password must contain more than "
                    + MIN_LENGTH + " characters!");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("Please enter the correct age! "
                    + "You must be " + MIN_AGE + " or older to register!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("You must be "
                    + MIN_AGE + " or older to register!");
        }
    }
}
