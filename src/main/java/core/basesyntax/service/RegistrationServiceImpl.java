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
        if (user == null
                || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RegistrationException("Some of the attributes are null");
        }

        return user.getLogin().length() >= MIN_LOGIN_LENGTH
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && user.getAge() >= MIN_USER_AGE
                && storageDao.get(user.getLogin()) == null;
    }
}
