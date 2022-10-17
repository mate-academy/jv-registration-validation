package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("User cannot be null");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().length() == 0) {
            throw new RuntimeException("Login cannot be empty");
        }
        if (Objects.nonNull(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User already exists in DB");
        }
        if (Objects.isNull(user.getAge()) || user.getAge() < MINIMUM_USER_AGE) {
            throw new RuntimeException("User must be adult (18+)");
        }
        if (Objects.isNull(user.getPassword())
                || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be 6 symbols at least");
        }
        return storageDao.add(user);
    }
}
