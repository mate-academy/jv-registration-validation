package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_PASSWORD_SIZE = 6;
    private static final int DEFAULT_LOGIN_SIZE = 6;
    private static final int MINIMUM_AGE_FOR_REGISTER = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("Such user is register");
        }
        if (user.getLogin() == null || user.getLogin().length() < DEFAULT_LOGIN_SIZE) {
            throw new ValidationException("Login must be longer than "
                    + DEFAULT_LOGIN_SIZE + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < DEFAULT_PASSWORD_SIZE) {
            throw new ValidationException("Password must be longer than "
                    + DEFAULT_PASSWORD_SIZE + " characters");
        }
        if (user.getAge() < MINIMUM_AGE_FOR_REGISTER) {
            throw new ValidationException("You must be older than " + MINIMUM_AGE_FOR_REGISTER);
        }
        storageDao.add(user);
        return user;
    }
}
