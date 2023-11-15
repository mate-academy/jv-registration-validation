package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.IncorrectDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public User register(User user) {
        try {
            userValidator.validate(user);
            storageDao.add(user);
            return user;
        } catch (IncorrectDataException e) {
            throw new RuntimeException("Incorrect data");
        }
    }
}
