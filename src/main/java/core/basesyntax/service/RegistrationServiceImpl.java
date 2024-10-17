package core.basesyntax.service;

import core.basesyntax.Exception.NoValidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMAL_AMOUNT_OF_SYMBOLS = 6;
    private static final int MINIMAL_AMOUNT_OF_AGE = 18;

    @Override
    public User register(User user) {
        for (User user1 : Storage.people) {
            if (user.getLogin().equals(user1.getLogin())) {
                throw new NoValidDataException("User with such login already exist");
            }
        }
        if (user.getLogin().length() < MINIMAL_AMOUNT_OF_SYMBOLS) {
            throw new NoValidDataException("Login should have at least 6 characters");
        }
        if (user.getPassword().length() < MINIMAL_AMOUNT_OF_SYMBOLS) {
            throw new NoValidDataException("Password should have at least 6 characters");
        }
        if (user.getAge() < MINIMAL_AMOUNT_OF_AGE) {
            throw new NoValidDataException("User need to be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
