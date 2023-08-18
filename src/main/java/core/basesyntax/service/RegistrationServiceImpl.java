package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AuthenticationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUMBER_OF_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new AuthenticationException("User should not be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new AuthenticationException("Login must not be shorter than: "
                    + MIN_NUMBER_OF_CHARACTERS + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new AuthenticationException("Password must not be shorter than: "
                    + MIN_NUMBER_OF_CHARACTERS + " characters");
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new AuthenticationException("Age of the user must be at least: " + MIN_AGE);
        }

        if (user.getId() == null) {
            throw new AuthenticationException("Id can`t be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException("Login is already in use");
        }
        return user;
    }
}
