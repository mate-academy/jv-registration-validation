package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login "
                    + user.getLogin()
                    + " already register");
        }
        if (user.getLogin().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User login can't be less than 6 characters!");
        }
        if (user.getPassword().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User password can't be less than 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age should be more than 18");
        }
        storageDao.add(user);
        return user;
    }
}
