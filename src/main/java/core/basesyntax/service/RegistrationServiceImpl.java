package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user.getLogin() != null && user.getLogin().length() < MIN_LENGTH) {
            throw new ValidationException(
                    "Login shorter than " + MIN_LENGTH + " symbols - " + user.getLogin());
        }
        if (user.getPassword() != null && user.getPassword().length() < MIN_LENGTH) {
            throw new ValidationException(
                    "Password shorter than " + MIN_LENGTH + " symbols - " + user.getPassword());
        }
        if (user.getAge() != null && user.getAge() < MIN_AGE) {
            throw new ValidationException("Age is incorrect - " + user.getAge());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("We already have such user - " + user.getLogin());
        }
        return storageDao.add(user);
    }
}
