package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int MINIMUM_LENGTH_PASSWORD = 6;
    private static final int MINIMUM_LENGTH_LOGIN = 1;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getAge() == null
                || user.getAge() < VALID_AGE
                || user.getLogin() == null
                || user.getLogin().length() < MINIMUM_LENGTH_LOGIN
                || user.getPassword() == null
                || user.getPassword().length() < MINIMUM_LENGTH_PASSWORD
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Invalid data");
        }
        return storageDao.add(user);
    }
}
