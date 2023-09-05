package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RegistrationException("We have user with such login");
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException(
                    String.format("Login must to have at least %d characters",
                            MIN_LENGTH_LOGIN));
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException(
                    String.format("Password must to have at least %d characters",
                            MIN_LENGTH_PASSWORD));
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Age can't be less than 0");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User must to be 18 or older");
        }
    }
}
