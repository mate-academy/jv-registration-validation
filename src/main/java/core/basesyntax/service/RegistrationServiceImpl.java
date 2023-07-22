package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin().length() < DEFAULT_MIN_CHARACTERS
                || user.getPassword().length() < DEFAULT_MIN_CHARACTERS
                || user.getAge() < 18) {
            throw new ValidationException("User login and password must be at least 6 characters.");
        }
        if (!(storageDao.get(user.getLogin()) == null)
                && user.getLogin().equals(storageDao.get(user.getLogin()).getLogin())) {
            throw new ValidationException("Same login was already registered, please try again");
        }
        return storageDao.add(user);
    }
}
