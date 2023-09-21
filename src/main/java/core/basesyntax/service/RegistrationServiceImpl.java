package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_LOGIN_LENGTH = 6;
    static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (Objects.isNull(user)) {
            throw new InvalidDataException("User can't be null");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Login must not be 'null' or shorter than 6 symbols");
        }
        if (Objects.isNull(user.getPassword())
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password must not be 'null' or shorter than 6 symbols");
        }
        if (Objects.isNull(user.getAge()) || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age must not be 'null' or less 18");
        }
        if (null != storageDao.get(user.getLogin())) {
            throw new InvalidDataException("User already registered.");
        }
        storageDao.add(user);
        return user;
    }
}
