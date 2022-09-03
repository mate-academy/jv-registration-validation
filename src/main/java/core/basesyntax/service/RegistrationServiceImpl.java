package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_USER_AGE = 18;
    public static final int MIN_USER_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getPassword() == null || user.getLogin() == null) {
            throw new RuntimeException("All fields should be initialized");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already existing");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Sorry, you are too young =)");
        }
        if (user.getPassword().length() < MIN_USER_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password is weak. Choose a better one");
        }
        storageDao.add(user);
        return user;
    }
}
