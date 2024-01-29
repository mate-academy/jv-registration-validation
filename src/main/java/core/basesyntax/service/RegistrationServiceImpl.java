package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String NULL_USER_MESSAGE = "User can't be null";
    private static final String LOGIN_MESSAGE = "User login must have at least"
            + LOGIN_MIN_LENGTH + "characters";
    private static final String PASSWORD_MESSAGE = "User password must have at least"
            + PASSWORD_MIN_LENGTH + "characters";
    private static final String AGE_MESSAGE = "User must be at least"
            + MIN_AGE + "years old";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkData(user);
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        throw new RegistrationException("User with this login is already registered!");
    }

    private void checkData(User user) {
        if (user == null) {
            throw new RegistrationException(NULL_USER_MESSAGE);
        } else if (user.getLogin() == null
                || user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException(LOGIN_MESSAGE);
        } else if (user.getPassword() == null
                || user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException(PASSWORD_MESSAGE);
        } else if (user.getAge() == null
                || user.getAge() < MIN_AGE) {
            throw new RegistrationException(AGE_MESSAGE);
        }
    }
}
