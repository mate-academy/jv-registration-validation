package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User is null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login "
                    + user.getLogin() + " already exists");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age " + user.getAge()
                    + " is less then " + MIN_AGE);
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password " + user.getPassword() + " can't be null");
        }

        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RegistrationException("The password must contains min "
                    + MIN_LENGTH_OF_PASSWORD + " characters");
        }
        return storageDao.add(user);
    }
}
