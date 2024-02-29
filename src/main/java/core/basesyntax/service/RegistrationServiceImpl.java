package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINLENGTH_PASSWORD = 6;
    public static final int MINIMUM_USERAGE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User with username '"
                    + user.getLogin() + "' already exists.");
        }

        if (user.getPassword().length() < MINLENGTH_PASSWORD) {
            throw new PasswordLengthException("Password should be at least "
                    + MINLENGTH_PASSWORD + " characters long.");
        }

        if (user.getAge() <= MINIMUM_USERAGE) {
            throw new IllegalArgumentException("The Age of user is not valid");
        }
        return user;
    }
}
