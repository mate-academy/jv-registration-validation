package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;
    private final UserValidator userValidator;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
        userValidator = new UserValidator();
    }

    @Override
    public User register(User user) {
        userValidator.validate(user);
        storageDao.add(user);
        return user;
    }
}
