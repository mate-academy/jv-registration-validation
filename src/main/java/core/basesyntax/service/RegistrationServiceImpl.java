package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegisterValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_LOW_LIMIT = 18;
    private static final int AGE_UP_LIMIT = 80;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegisterValidationException("User must be Not null");
        }
        if (user.getAge() == null || user.getAge() < AGE_LOW_LIMIT || user.getAge() > AGE_UP_LIMIT) {
            throw new RegisterValidationException("You enter wrong age");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new RegisterValidationException("You enter wrong login");
        }
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new RegisterValidationException("You enter wrong password");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterValidationException("Login already exist!");
        }
        storageDao.add(user);
        return user;
    }
}
