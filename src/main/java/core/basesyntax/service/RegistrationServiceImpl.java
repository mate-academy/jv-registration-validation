package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int VALID_LOGIN_LENGTH = 6;
    public static final int VALID_PASSWORD_LENGTH = 6;
    public static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        Objects.requireNonNull(user);

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterException("User with " + user.getLogin()
                    + " as a login already exist");
        }

        if (user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new RegisterException(
                    "The login must have at least " + VALID_LOGIN_LENGTH
                            + " symbols, but you have: "
                            + user.getLogin().length());
        }

        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RegisterException(
                    "The password must have at least " + VALID_PASSWORD_LENGTH
                            + " symbols, but you have: "
                            + user.getPassword().length());
        }

        if (user.getAge().compareTo(VALID_AGE) < 0) {
            throw new RegisterException(
                    "The age must have at least " + VALID_AGE
                            + " years old, but yours is : "
                            + user.getAge());
        }

        return storageDao.add(user);
    }
}
