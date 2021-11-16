package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_VALID_AGE = 18;
    private final static int MIN_SYMBOLS_AT_PASSWORD = 6;
    private final static int MIN_LOGIN_LENGTH = 1;

    @Override
    public User register(User user) throws RuntimeException{
        StorageDao storageDao = new StorageDaoImpl();
        if (user.getAge() == null || user.getPassword() == null || user.getLogin() == null) {
            throw new RuntimeException("Input data is null");
        }
        if (storageDao.get(user.getLogin()) == null
                && user.getAge() >= MIN_VALID_AGE
                && user.getPassword().length() >= MIN_SYMBOLS_AT_PASSWORD
                && user.getLogin().length() >= MIN_LOGIN_LENGTH) {
            return storageDao.add(user);
        }
            throw new RuntimeException("Data is invalid");
    }
}
