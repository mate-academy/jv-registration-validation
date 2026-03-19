package core.basesyntax.service;

import core.basesyntax.appexception.RegisterException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegisterException("User login should not be NULL");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegisterException("User login should not be LESS than 6 letter");
        }
        User userLogin = storageDao.get(user.getLogin());
        if (userLogin != null) {
            throw new RegisterException("There is already user with such login");
        }
        if (user.getPassword() == null) {
            throw new RegisterException("User password should not be NULL");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegisterException("User password should not be LESS than 6 letter");
        }
        if (user.getAge() == null) {
            throw new RegisterException("user's age can't be null");
        }
        if (user.getAge() < 0) {
            throw new RegisterException("user's age can't be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterException("user's age can't be LESS than 18");
        }

        storageDao.add(user);

        return user;
    }
}
