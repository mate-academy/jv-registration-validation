package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;


public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getAge() < MIN_VALID_AGE
                || user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getLogin() == null || user.getLogin().isEmpty()
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Invalid input data!");
        }
        return storageDao.add(user);
    }
}
