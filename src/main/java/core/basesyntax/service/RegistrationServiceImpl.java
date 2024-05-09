package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_NUMBER_OF_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("All fields must be filled");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already registered");
        }
        if (user.getLogin().length() < MIN_NUMBER_OF_CHARACTERS
                || user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("Username or password does not meet the criteria");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age cannot be less than " + MIN_AGE);
        }
        int ret1 = user.getLogin().indexOf("@");
        if (ret1 < 0) {
            throw new RegistrationException("Invalid login");
        }
        return storageDao.add(user);
    }
}
