package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final IUserValidation userValidation = new UserValidation();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User validatedUser = userValidation.validate(user);
        return storageDao.add(validatedUser);
    }
}
