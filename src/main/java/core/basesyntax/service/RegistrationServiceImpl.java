package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE_VALUE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getPassword() == null || user.getLogin() == null) {
            throw new NullPointerException("Data is not valid");
        } else if (user.getAge() < MINIMUM_AGE_VALUE) {
            throw new RuntimeException("User must have age more than " + MINIMUM_AGE_VALUE);
        } else if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must have more than "
                    + MINIMUM_PASSWORD_LENGTH + " symbols");
        } else {
            for (User getUser : Storage.people) {
                if (getUser.getLogin().equals(user.getLogin())) {
                    throw new RuntimeException("User with login "
                            + user.getLogin() + " is already exists");
                }
            }
            return storageDao.add(user);
        }
    }
}
