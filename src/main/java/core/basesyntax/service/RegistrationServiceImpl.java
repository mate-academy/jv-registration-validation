package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE_FOR_REGISTRATION = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException(
                    "User is null");
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new RegistrationException(
                    "Can't registration user, because one of the criteria is null");
        } else if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException(
                    "Can't registration user, because password is less than 6 letters");
        } else if (user.getAge() == null || user.getAge() < MIN_AGE_FOR_REGISTRATION) {
            throw new RegistrationException(
                    "Can't registration user, because user isn't 18 years ago");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "Can't registration user, because this login already uses in storage");
        }
        return storageDao.add(user);
    }
}
