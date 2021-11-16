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
        if (user == null
                || user.getAge() == null
                || user.getAge() < VALID_AGE
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid data entered. :(");
        }
        return user;
    }
}
