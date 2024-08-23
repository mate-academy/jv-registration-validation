package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!checkDataValidity(user)) {
            throw new RegistrationException("The user data is not valid");
        }
        return storageDao.add(user);
    }

    private boolean checkDataValidity(User user) {
        if (user == null) {
            throw new RegistrationException("The user is null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password is invalid");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The login is invalid");
        }

        return user.getLogin().length() >= MIN_LOGIN_LENGTH
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && user.getAge() >= MIN_USER_AGE
                && storageDao.get(user.getLogin()) == null;
    }
}
