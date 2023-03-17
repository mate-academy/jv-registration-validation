package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t  be null");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationException("Not valid password!");
        }

        if (user.getAge() < MIN_USER_AGE && user.getAge() != null) {
            throw new RegistrationException(" Not valid age");
        }

        if (user.getAge() == null) {
            throw new NullPointerException("Age can`t be null ");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        User result = storageDao.add(user);
        return result;
    }
}
