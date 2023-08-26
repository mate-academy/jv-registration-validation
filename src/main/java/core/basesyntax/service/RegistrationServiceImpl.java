package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cant be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already exist");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can`t be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("Login should contain at least 6 symbols");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can`t be null and should contain at least 6 symbols");
        }
        if (user.getPassword().length() <= MIN_LENGTH) {
            throw new InvalidDataException("Password should contain at least 6 symbols");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("You must be at least 18");
        }
        return storageDao.add(user);
    }
}
