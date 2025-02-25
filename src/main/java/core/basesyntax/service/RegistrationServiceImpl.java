package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_AMOUNT_OF_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getId() == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new UserNotProperException("User contains null fields");
        }

        if (storageDao.get(user.getLogin()) == null
                && user.getLogin().length() >= MIN_AMOUNT_OF_SYMBOLS
                && user.getPassword().length() >= MIN_AMOUNT_OF_SYMBOLS
                && user.getAge() >= MIN_AGE) {
            storageDao.add(user);
            return user;
        } else {
            throw new UserNotProperException("User is already exist "
                    + "/ login/password length is short "
                    + "/ user is so young for registration");
        }
    }
}
