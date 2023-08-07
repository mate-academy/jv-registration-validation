package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.utils.UserValidator;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public User register(User user) {
        userValidator.checkAge(user);
        userValidator.checkLogin(user);
        userValidator.checkPassword(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
