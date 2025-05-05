package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_ACCEPT_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login is already registered");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("This field can't be empty or null");
        }
        if (user.getAge() == null || user.getAge() <= MIN_ACCEPT_AGE) {
            throw new RuntimeException("Your age can't be null or less than 19");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password if too short"
                    + ", it must contains at least 6 symbols");
        }
        return storageDao.add(user);
    }
}
