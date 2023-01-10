package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private ValidationService validationService = new ValidationServiceImpl();

    @Override
    public User register(User user) {
        validationService.validateUser(user);
        return storageDao.add(user);
    }

}
