package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.excpt.NotValidUserData;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_AGE = 18;
    private final static int MIN_CHAR_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new NotValidUserData("User Age can't be null");
        }
        if (user.getLogin() == null) {
            throw new NotValidUserData("User Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new NotValidUserData("User Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidUserData("There is already a user with the given name");
        }
        if (user.getLogin().length() < MIN_CHAR_LENGTH) {
            throw new NotValidUserData("User login must have at least 6 characters");
        }
        if (user.getPassword().length() < MIN_CHAR_LENGTH) {
            throw new NotValidUserData("User password must have at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotValidUserData("User must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
