package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final short MIN_LOGIN_LENGTH = 6;
    private static final short MIN_PASSWORD_LENGTH = 6;
    private static final short MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new ValidationException("User data can't be null!");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("User login can't be less than 6 symbols!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("User password can't be less than 6 symbols!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User age can't be less than 18 years old!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with such login is already exist!");
        }
        return storageDao.add(user);
    }
}
