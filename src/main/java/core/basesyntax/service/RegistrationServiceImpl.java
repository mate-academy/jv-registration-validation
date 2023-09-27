package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidUserDataException("Password cant be less than 6");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidUserDataException("Login cant be less than 6");
        }
        return storageDao.add(user);
    }
}
