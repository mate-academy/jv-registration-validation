package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("user should be not null");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new InvalidDataException("user has null value");
        }
        if (user.getLogin().length() < 1) {
            throw new InvalidDataException("user have to have login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("there already is user with same login in storage");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new InvalidDataException("user age is under 18");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new InvalidDataException("user password should be at least 6 characters");
        }
        storageDao.add(user);
        return user;
    }
}
