package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final int MINIMUM_USER_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("You tried to register user with NULL");
        }
        if (user.getAge() < MINIMUM_USER_AGE || user.getAge() == null) {
            throw new RuntimeException("You can not register user who is less than "
                + MINIMUM_USER_AGE);
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password is too short. It should be at least "
                + MINIMUM_PASSWORD_LENGTH + "symbols.");
        }
        if (user.getLogin() != null) {
            for (User person : Storage.people) {
                if (user.getLogin().toLowerCase().equals(person.getLogin().toLowerCase())) {
                    throw new RuntimeException("The login you want to use is already taken");
                }
            }
        } else {
            throw new RuntimeException("Login is null");
        }
        return storageDao.add(user);
    }
}
