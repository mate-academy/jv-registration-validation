package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD = 6;
    private static final int MINIMUM_USER_AGE_TO_LOGIN = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserForNullValue(user);

        if (checkUserLogin(user) && checkUserPassword(user) && checkUserAge(user)) {
            storageDao.add(user);
        } else {
            throw new InvalidDataException("it is impossible to register a user "
                    + "due to non-compliance with the registration policy");
        }
        return user;
    }

    public void checkUserForNullValue(User user) {
        if (user == null) {
            throw new InvalidDataException("User should be not null");
        }
    }

    public boolean checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login should be not null");
        }
        return user.getLogin().length() >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD;
    }

    public boolean checkUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("User password should be not null");
        }
        return user.getPassword().length() >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD;
    }

    public boolean checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("User age should be not null");
        }
        return user.getAge() >= MINIMUM_USER_AGE_TO_LOGIN;
    }
}
