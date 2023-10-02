package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegisterException("Login should be not null");
        }
        if (user.getLogin().equals(storageDao.get(user.getLogin()).getLogin())) {
            throw new RegisterException("This user exists in DB");
        }
        if (user.getLogin().length() < LENGTH) {
            throw new RegisterException("Login length should be greater als " + LENGTH);
        }
        if (user.getPassword() == null) {
            throw new RegisterException("Password should be not null");
        }
        if (user.getPassword().length() < LENGTH) {
            throw new RegisterException("Password length should be greater als " + LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterException("User Age should be greater als " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
