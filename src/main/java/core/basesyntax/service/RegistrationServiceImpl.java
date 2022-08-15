package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        storageDao.add(user);
        return user;
    }
}
