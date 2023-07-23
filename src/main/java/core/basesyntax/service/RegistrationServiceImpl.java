package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_VALID_LENGTH = 6;
    public static final int MINIMUM_ADULT_USERS_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationUserException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationUserException("Login can't be null");
        }
        if (user.getLogin().length() < MINIMUM_VALID_LENGTH) {
            throw new RegistrationUserException("Login can't be shorter than "
                    + MINIMUM_VALID_LENGTH + "characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException("User with login "
                    + user.getLogin() + " already exist");
        }
        if (user.getPassword() == null) {
            throw new RegistrationUserException("User password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_VALID_LENGTH) {
            throw new RegistrationUserException("Password can't be shorter than "
                    + MINIMUM_VALID_LENGTH + "characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationUserException("User age can't be null");
        }
        if (user.getAge() < MINIMUM_ADULT_USERS_AGE) {
            throw new RegistrationUserException("User age can't be less than "
                    + MINIMUM_ADULT_USERS_AGE + "years old");
        }
        return storageDao.add(user);
    }
}
