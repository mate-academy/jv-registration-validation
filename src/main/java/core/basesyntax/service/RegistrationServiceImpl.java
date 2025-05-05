package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 110;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is invalid."
                    + " Password can't be less than 6 characters");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login is invalid."
                    + " Login can't be less than 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RegistrationException(String.format("Age is invalid."
                    + " Min allowed age is: %s."
                    + " Max allowed age is: %s", MIN_AGE, MAX_AGE));
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(String.format("User with login %s already exists",
                    user.getLogin()));
        }
        if (user.getLogin().length() >= MIN_LOGIN_LENGTH
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH) {
            storageDao.add(user);
        }
        return user;
    }
}
