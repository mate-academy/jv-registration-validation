package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RuntimeException("Invalid input data.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists :(");
        }
        if (user.getAge() >= VALID_AGE
                && user.getPassword().length() >= VALID_PASSWORD_LENGTH) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Something went wrong...");
    }
}
