package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || password == null || age == null) {
            throw new RegistrationException("User login, password and age can't be null");
        }

        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User login shouldn't be less than "
                    + MIN_LOGIN_LENGTH + " characters");
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User password shouldn't be less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }

        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with login " + login + " already registered");
        }

        if (age < MIN_AGE) {
            throw new RegistrationException("User's age can't be less than " + MIN_AGE);
        }

        if (age > MAX_AGE) {
            throw new RegistrationException("User's age can't be more than " + MAX_AGE);
        }

        return storageDao.add(user);
    }
}
