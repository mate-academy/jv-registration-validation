package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MAX_SYMBOLS = 6;
    public static final int MIN_AGE = 18;
    public final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (isLoginExist(user.getLogin())) {
            throw new RegistrationException("User with such login is already exist");
        }
        if (user.getLogin().length() < MAX_SYMBOLS) {
            throw new RegistrationException("This login has lower than " + MAX_SYMBOLS
                    + " symbols");
        }
        if (user.getPassword().length() < MAX_SYMBOLS) {
            throw new RegistrationException("This password has lower than " + MAX_SYMBOLS
                    + " symbols");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age need to be higher than " + MIN_AGE
            + " to pass registration");
        }

        storageDao.add(user);
        return user;
    }

    private boolean isLoginExist(String login) {
        User user = storageDao.get(login);
        return user != null && user.getLogin().equals(login);
    }
}
