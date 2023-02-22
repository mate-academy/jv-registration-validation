package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegisterValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_LOW_LIMIT = 18;
    private static final int AGE_UP_LIMIT = 80;
    private static final String SPACE = " ";
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegisterValidationException("User must be Not null");
        }
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user.getAge() == null
                || user.getAge() < AGE_LOW_LIMIT
                || user.getAge() > AGE_UP_LIMIT) {
            throw new RegisterValidationException("You enter wrong age. Expected between "
                    + AGE_LOW_LIMIT + " and " + AGE_UP_LIMIT + " but was " + user.getAge());
        }
        if (user.getLogin() == null || user.getLogin().contains(SPACE)) {
            throw new RegisterValidationException("Your login must not contain a space");
        }
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new RegisterValidationException("You password must be more then 8 character");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterValidationException("Login already exist!");
        }
    }
}
