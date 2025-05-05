package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.validators.UserValidator;
import core.basesyntax.service.validators.Validator;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final Validator<User> userValidator = new UserValidator(storageDao);

    @Override
    public User register(User user) {
        userValidator.validate(user);
        storageDao.add(user);
        return user;
    }

}
