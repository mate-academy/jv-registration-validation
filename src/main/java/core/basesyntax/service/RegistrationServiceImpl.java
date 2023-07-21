package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserInvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_AGE = 18;
    public static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserInvalidDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new UserInvalidDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserInvalidDataException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new UserInvalidDataException("Age can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidDataException("User " + user.getLogin() + " already registered");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new UserInvalidDataException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MINIMUM_AGE);
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new UserInvalidDataException("Not valid Login: " + user.getLogin()
                    + ". Min allowed Login is " + MINIMUM_LENGTH);
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new UserInvalidDataException("Not valid password: " + user.getPassword()
                    + ". Min allowed password is " + MINIMUM_LENGTH);
        }
        return storageDao.add(user);
    }
}
