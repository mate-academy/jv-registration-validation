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
            throw new RegistrationDataException("User can't be null");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationDataException("Login must not be 'null' or shorter than "
                    + MIN_LOGIN_LENGTH + " symbols");
        }
        if (Objects.isNull(user.getPassword())
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationDataException("Password must not be 'null' or shorter than "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
        if (Objects.isNull(user.getAge()) || user.getAge() < MIN_AGE) {
            throw new RegistrationDataException("Age must not be 'null' or less " + MIN_AGE);
        }
        if (null != storageDao.get(user.getLogin())) {
            throw new RegistrationDataException("User already registered.");
        }
        storageDao.add(user);
        return user;
    }
}
