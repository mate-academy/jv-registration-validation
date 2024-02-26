package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int USERPASSWORD_LENGTH = 6;
    public static final int USER_AGE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException(user.getLogin());
        }

        if (user.getPassword().length() < USERPASSWORD_LENGTH) {
            throw new PasswordLengthException(6);
        }

        if (user.getAge() <= USER_AGE) {
            throw new IllegalArgumentException("The Age of user is not valid");
        }
        return user;
    }
}
