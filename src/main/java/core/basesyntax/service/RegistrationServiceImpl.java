package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SYMBOLS_FOR_LOGIN = 6;
    private static final int THE_LOWEST_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't add a user, user can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Can't add a user, the user is already added");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Can't add a user, age of user can not be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Can't add a user, login can't be null");
        }
        if (user.getAge() < THE_LOWEST_AGE) {
            throw new RuntimeException("Can't add a user, the user is younger then "
                    + THE_LOWEST_AGE);
        }
        if (user.getPassword().length() < MIN_SYMBOLS_FOR_LOGIN) {
            throw new RuntimeException("Can't add a user, password is shorter then "
                    + MIN_SYMBOLS_FOR_LOGIN + " symbols");
        }
        storageDao.add(user);
        return user;
    }
}
