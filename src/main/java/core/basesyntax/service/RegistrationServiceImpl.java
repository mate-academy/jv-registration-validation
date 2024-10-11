package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR_COUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be null"
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
        if (user.getPassword() == null ||  user.getPassword().isEmpty()) {
            throw new RegistrationException("Password can't be null"
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null"
                    + "Min age is "
                    + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getLogin().length() < MIN_CHAR_COUNT) {
            throw new RegistrationException("Login is too short"
                    + user.getLogin()
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
        if (user.getPassword().length() < MIN_CHAR_COUNT) {
            throw new RegistrationException("Password is too short"
                    + user.getPassword()
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is "
                    + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
