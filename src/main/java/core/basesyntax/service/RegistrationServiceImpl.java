package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegisterException("empty login");
        }
        if (user.getPassword() == null) {
            throw new RegisterException("empty password");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterException("the age is under " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RegisterException("password length must be equals or more than " + MIN_PASS_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterException("the " + user.getLogin() + " already exist in storage");
        }
        return storageDao.add(user);
    }
}
