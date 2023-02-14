package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.validator.UserValidator;
import core.basesyntax.validator.Validator;

public class RegistrationServiceImpl implements RegistrationService {
    private final Validator validator = new UserValidator();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (validator.isValid(user)) {
            user.setLogin(user.getLogin().strip());
            user.setPassword(user.getPassword().strip());
            storageDao.add(user);
            return user;
        }
        return null;
    }
}
