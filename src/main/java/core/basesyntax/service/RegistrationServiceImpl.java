package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_AMOUNT_OF_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNotProperException("User contains null fields");
        }

        if (user.getId() == null) {
            throw new UserNotProperException("User ID field is null");
        }

        if (user.getLogin() == null) {
            throw new UserNotProperException("User login field is null");
        }

        if (user.getPassword() == null) {
            throw new UserNotProperException("User password field is null");
        }

        if (user.getAge() == null) {
            throw new UserNotProperException("User age field is null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserNotProperException("User is already exist");
        }

        if (user.getLogin().length() < MIN_AMOUNT_OF_SYMBOLS) {
            throw new UserNotProperException("Login is short");
        }

        if (user.getPassword().length() < MIN_AMOUNT_OF_SYMBOLS) {
            throw new UserNotProperException("Password is short");
        }

        if (user.getAge() < MIN_AGE) {
            throw new UserNotProperException("User is so young for registration");
        }
        storageDao.add(user);
        return user;
    }
}
