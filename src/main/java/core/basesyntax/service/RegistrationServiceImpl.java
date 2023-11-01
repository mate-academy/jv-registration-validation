package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        return null;
    }
}
