package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_RESTRICTOR = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getPassword() == null
                || user.getLogin() == null
                || user.getAge() == null) {
            throw new NullPointerException("Data is incorrect");
        }
        if (user.getAge() < AGE_RESTRICTOR) {
            throw new RuntimeException("Your age is under " + AGE_RESTRICTOR + " years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("your password is not correct, it must have more than "
                    + MIN_PASSWORD_LENGTH
                    + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Account with that login is taken");
        }
        return storageDao.add(user);
    }
}
