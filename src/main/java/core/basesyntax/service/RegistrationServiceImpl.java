package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You must be older or equal 18");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Empty login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exist");
        }
        if (user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationException("Password must contain at least six characters");
        }
        storageDao.add(user);
        return user;
    }
}
