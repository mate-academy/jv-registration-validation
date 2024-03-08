package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LOGIN_LENGTH = 2;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null
                || user.getPassword().length() < VALID_PASSWORD_LENGTH
                || user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new ValidationException("Validation error");
        }
        storageDao.add(user);
        return user;
    }
}
