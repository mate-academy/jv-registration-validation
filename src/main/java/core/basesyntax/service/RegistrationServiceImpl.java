package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MINIMUM_USERAGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationExceprtion("User with username '"
                    + user.getLogin() + "' already exists.");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationExceprtion("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters long.");
        }

        if (user.getAge() <= MINIMUM_USERAGE) {
            throw new IllegalArgumentException("The Age of user is not valid");
        }
        storageDao.add(user);
        return user;
    }
}
