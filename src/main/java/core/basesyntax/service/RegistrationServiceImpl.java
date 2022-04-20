package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;

    @Override
    public User register(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException();
        }
        if (storageDao.get(user.getLogin()) == null
                || storageDao.get(user.getLogin()).equals(user.getLogin())) {
            if (user.getAge() >= MIN_AGE) {
                if (user.getPassword().length() >= MIN_PASS_LENGTH) {
                    storageDao.add(user);
                    return user;
                }
                throw new RuntimeException("Min pass length " + MIN_PASS_LENGTH
                        + ", but was "
                        + user.getPassword().length());
            }
            throw new RuntimeException("The age must be at least 18 but was " + user.getAge());
        }
        throw new RuntimeException("This login is already exist");
    }
}
