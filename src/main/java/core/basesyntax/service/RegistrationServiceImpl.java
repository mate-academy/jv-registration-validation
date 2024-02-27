package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_NUMBER_OF_CHARACTERS = 6;
    private static final int MINIMUM_REGISTRATION_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null");
        }

        if (user.getLogin().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("Login length can be less than "
                    + MINIMUM_NUMBER_OF_CHARACTERS);
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null");
        }

        if (user.getPassword().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("Password length can be less than "
                    + MINIMUM_NUMBER_OF_CHARACTERS);
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age is null");
        }

        if (user.getAge() < MINIMUM_REGISTRATION_AGE) {
            throw new RegistrationException("Age can be less than " + MINIMUM_REGISTRATION_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }

        storageDao.add(user);
        return user;
    }
}
