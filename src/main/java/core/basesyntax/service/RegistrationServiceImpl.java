package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int YEAR = 1;
    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR_TO_CREATE_LOGIN_OR_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Needed login for registration process");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Needed password for registration process");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Try another one");
        }
        if (user.getLogin().length() < MIN_CHAR_TO_CREATE_LOGIN_OR_PASSWORD) {
            throw new RegistrationException("Login should have at least 6 characters");
        }
        if (user.getPassword().length() < MIN_CHAR_TO_CREATE_LOGIN_OR_PASSWORD) {
            throw new RegistrationException("Password should have at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Enter Your age");
        }
        if ((user.getAge() < MIN_AGE) && (MIN_AGE - user.getAge() != YEAR)) {
            throw new RegistrationException("Come again in "
                    + (MIN_AGE - user.getAge()) + "years");
        } else if (MIN_AGE - user.getAge() == YEAR) {
            throw new RegistrationException("Come again in " + (MIN_AGE - user.getAge() + "year"));
        }
        return storageDao.add(user);
    }
}
