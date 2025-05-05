package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new RuntimeException("Invalid data");
        }
        if (user.getPassword() == null
                || user.getPassword().trim().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException(
                    String.format("User password should be at least %s characters",
                            MIN_LENGTH_PASSWORD));
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException(
                    String.format("User age should be at least %s years old",
                            MIN_AGE));
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(String.format("User with login=%s was registered",
                    user.getLogin()));
        }
        return storageDao.add(user);
    }
}
