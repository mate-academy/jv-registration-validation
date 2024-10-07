package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new RegistrationException("Login or password cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already taken");
        }
        if (user.getLogin().length() < MIN_CHARACTERS_LENGTH
                || user.getPassword().length() < MIN_CHARACTERS_LENGTH) {
            throw new RegistrationException("Login or password characters less than 6");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age is less than 18");
        }
        return storageDao.add(user);
    }
}
