package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARS = 6;
    private static final String ERROR_MESSAGE = "Invalid data, user not registered!";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getAge() < MIN_AGE
                || user.getPassword().length() < MIN_CHARS
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
        return storageDao.add(user);
    }
}
