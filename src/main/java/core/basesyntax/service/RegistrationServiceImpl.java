package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_OR_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be a null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login can't be a null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password can't be a null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age can't be a null");
        }
        if (user.getLogin().length() < MIN_LOGIN_OR_PASSWORD_LENGTH) {
            throw new RegistrationException("User's login must have " + MIN_LOGIN_OR_PASSWORD_LENGTH
            + " or more characters");
        }
        if (user.getPassword().length() < MIN_LOGIN_OR_PASSWORD_LENGTH) {
            throw new RegistrationException("User's password must have "
                    + MIN_LOGIN_OR_PASSWORD_LENGTH + " or more characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be equal or more than " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login: "
                    + user.getLogin() + " is existed in Storage");
        }
        return storageDao.add(user);
    }
}
