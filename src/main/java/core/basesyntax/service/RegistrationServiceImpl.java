package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (checkUser(user)) {
            storageDao.add(user);
        }
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getId() == null
                || user.getAge() == null) {
            throw new RuntimeException("Invalid data, check all user values");
        }
        return user;
    }

    private boolean checkUser(User user) {
        if ((storageDao.get(user.getLogin()) == null)
                && (user.getAge() >= MIN_AGE)
                && (user.getPassword().length() >= MIN_PASSWORD_LENGTH)) {
            return true;
        } else {
            throw new RuntimeException("Invalid data, check some fields");
        }
    }
}
