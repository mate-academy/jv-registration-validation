package core.basesyntax.service;

import core.basesyntax.RegistrationUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword().length() < 6) {
            throw new RegistrationUserException(
                    "Can't registration user, because password is less than 6 letters");
        } else if (user.getAge() < 18) {
            throw new RegistrationUserException(
                    "Can't registration user, because user isn't 18 years ago");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException(
                    "Can't registration user, because this login already uses in storage");
        }
        return storageDao.add(user);
    }
}
