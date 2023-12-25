package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Bad data, try again");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("You are too young to registration,"
                    + " age must be no less " + MIN_USER_AGE);
        }
        if (user.getLogin().length() < VALID_LENGTH) {
            throw new RegistrationException("I`ts a short login, minimal length " + VALID_LENGTH);
        }
        if (user.getPassword().length() < VALID_LENGTH) {
            throw new RegistrationException("I`ts a short password,"
                    + " minimal length " + VALID_LENGTH);
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("You forgot enter login, try again.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationException("You forgot enter password, try again.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This user already exists");
        }
        storageDao.add(user);
        return user;
    }
}
