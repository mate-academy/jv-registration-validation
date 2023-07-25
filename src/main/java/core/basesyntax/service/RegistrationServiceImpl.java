package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_FIELD_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;
    private static final String EMPTY_STRING = "";

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getLogin().equals(EMPTY_STRING)) {
            throw new RegistrationException("Login can't be empty");
        }
        if (user.getPassword().equals(EMPTY_STRING)) {
            throw new RegistrationException("Password can't be empty");
        }
        if (user.getPassword().length() < MIN_FIELD_LENGTH) {
            throw new RegistrationException("Password must be longer than "
                    + MIN_FIELD_LENGTH);

        }
        if (user.getLogin().length() < MIN_FIELD_LENGTH) {
            throw new RegistrationException("Login must be longer than "
                    + MIN_FIELD_LENGTH);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age can't be null");
        }
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RegistrationException("User age must be greater than "
                    + MIN_VALID_AGE);
        }
        if (!(storageDao.get(user.getLogin()) == null)) {
            throw new RegistrationException("User with login: " + user.getLogin()
                    + " already exists");
        }
        return storageDao.add(user);
    }
}
