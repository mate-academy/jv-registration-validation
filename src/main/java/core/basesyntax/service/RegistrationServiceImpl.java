package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_VALID_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User is already exist");
        }
        if (user.getAge() <= MIN_VALID_AGE) {
            throw new RuntimeException("Users age is less than 18 years old");
        }
        if (user.getPassword().length() <= MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Users password is less than 6 symbols");
        }
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getLogin().isEmpty()
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("This field should not be empty");
        }
        return storageDao.add(user);
    }
}
