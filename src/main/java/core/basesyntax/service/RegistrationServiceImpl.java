package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationValidationExeption;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_LOW_LIMIT = 18;
    private static final int AGE_UP_LIMIT = 100;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String SPACE = " ";
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationValidationExeption("User can't be null");
        }
        if (user.getAge() == null || user.getAge() < AGE_LOW_LIMIT
                || user.getAge() > AGE_UP_LIMIT) {
            throw new RegistrationValidationExeption("User's age (" + user.getAge()
                    + ") not valid. Expected between (" + AGE_LOW_LIMIT
                    + ") and (" + AGE_UP_LIMIT + ")");
        }
        if (user.getLogin() == null || user.getLogin().contains(SPACE)) {
            throw new RegistrationValidationExeption("Login invalid, delete spaces in login");
        }
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationValidationExeption("Please write password more than 8 symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidationExeption("User with this login already exist");
        }
    }
}
