package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_AGE = 140;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getAge() == null || user.getPassword() == null) {
            throw new RegistrationException("You need to fill all fields for registration");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User with age less then 18 can't be registered.");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RegistrationException("Invalid age input, check this field.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Your password's length should be 6 or more.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already exists, please, choose another");
        }
        return storageDao.add(user);
    }
}
