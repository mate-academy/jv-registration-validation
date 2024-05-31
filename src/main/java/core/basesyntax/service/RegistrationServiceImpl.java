package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOWED_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can not be null!");
        } else if (user.getAge() == null) {
            throw new InvalidDataException("Age can not be null!");
        } else if (user.getPassword() == null) {
            throw new InvalidDataException("Password can not be null!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("There already is user with this login!");
        } else if (user.getLogin().length() < ALLOWED_LENGTH) {
            throw new InvalidDataException("Login is less than 6 characters, try longer!");
        } else if (user.getPassword().length() < ALLOWED_LENGTH) {
            throw new InvalidDataException("Password is less than 6 characters, try longer!");
        } else if (user.getAge() < MIN_AGE && user.getAge() >= 0) {
            throw new InvalidDataException("User is under 18!");
        } else if (user.getAge() < 0) {
            throw new InvalidDataException("Age can not be negative!");
        }
        return storageDao.add(user);
    }
}
