package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_NUMBER_OF_CHARACTERS = 6;
    private static final int MINIMUM_AGE_FOR_REGISTRATION = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be initialized as null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("The number of characters in the login"
                    + " must be more than " + MINIMUM_NUMBER_OF_CHARACTERS);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("The number of characters in the password"
                    + " must be more than " + MINIMUM_NUMBER_OF_CHARACTERS);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMUM_AGE_FOR_REGISTRATION) {
            throw new RegistrationException("Age must be more than "
                    + MINIMUM_AGE_FOR_REGISTRATION);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such a login already exists");
        }

        storageDao.add(user);
        return user;
    }
}
