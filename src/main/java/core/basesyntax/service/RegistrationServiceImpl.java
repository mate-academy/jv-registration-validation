package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login can't be  less than 6 characters");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login " + user.getLogin()
                    + " already exists");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Age can't be less than 18");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password's length can't be less than 6 characters");
        }
        return storageDao.add(user);
    }
}
