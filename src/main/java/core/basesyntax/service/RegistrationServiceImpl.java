package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationServiceException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOWED_USERS_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User is null");
        }
        User alreadyHaveSuchUser = storageDao.get(user.getLogin());
        if (alreadyHaveSuchUser != null) {
            throw new RegistrationServiceException("Such login "
                    + user.getLogin() + " already created");
        }
        Integer usersAge = user.getAge();
        if (usersAge == null || usersAge < ALLOWED_USERS_AGE) {
            throw new RegistrationServiceException("Allowed users age is " + ALLOWED_USERS_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationServiceException("Short password length "
                    + user.getPassword().length()
                    + " needed length at least " + MIN_PASSWORD_LENGTH);
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("User login is null");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
