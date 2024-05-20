package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String USER_EXIST = "User already has been registered!";
    private final StorageDao storageDao;
    private final UserValidator userValidator;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
        userValidator = new UserValidatorImpl();
    }

    @Override
    public User register(User user) {
        userValidator.validate(user);
        if (isUserExist(user)) {
            throw new RuntimeException(USER_EXIST);
        }
        storageDao.add(user);
        return user;
    }

    private boolean isUserExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
