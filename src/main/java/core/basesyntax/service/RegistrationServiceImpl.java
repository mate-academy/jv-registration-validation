package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

import java.util.Comparator;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String USER_EXISTS_MSG = "User already exists in DB";
    private static final String USER_ADULT_MSG = "User must be adult (18+)";
    private static final String PASSWORD_LENGTH_MSG = "Password must be 6 symbols at least";
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (Objects.nonNull(storageDao.get(user.getLogin()))) {
            throw new RuntimeException(USER_EXISTS_MSG);
        }
        if (Objects.compare(user.getAge(), MINIMUM_USER_AGE, Comparator.naturalOrder()) < 0) {
            throw new RuntimeException(USER_ADULT_MSG);
        }
        if (Objects.compare(user.getPassword().length(), MINIMUM_PASSWORD_LENGTH, Comparator.naturalOrder()) < 0) {
            throw new RuntimeException(PASSWORD_LENGTH_MSG);
        }
        return storageDao.add(user);
    }
}
