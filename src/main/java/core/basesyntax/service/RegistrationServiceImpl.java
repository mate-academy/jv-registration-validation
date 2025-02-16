package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_REGISTRATION_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Please, enter password at least 6 characters long");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Please, indicate your age");
        }
        if (user.getAge() < MIN_REGISTRATION_AGE) {
            throw new RegistrationException("You must be "
                    + MIN_REGISTRATION_AGE
                    + " or older to register");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Please, enter valid age");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("You login must be at least 6 character long");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such user already exists");
        }
        return storageDao.add(user);
    }
}
