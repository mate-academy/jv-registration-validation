package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.validators.UserValidator;

public class RegistrationServiceImpl implements RegistrationService {

    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) throws InvalidUserException {
        new UserValidator().validateUser(user);
        storageDao.add(user);
        return user;
    }
}
