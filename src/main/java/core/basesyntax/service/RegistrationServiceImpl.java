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
            throw new RegistrationException("User must be initialized");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The login must be initialized");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such a login already exists");
        }
        if (user.getLogin().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("The number of characters in the login"
                    + " must be more than 6");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password must be initialized");
        }
        if (user.getPassword().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("The number of characters in the password"
                    + " must be more than 6");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The age must be initialized");
        }
        if (user.getAge() < MINIMUM_AGE_FOR_REGISTRATION) {
            throw new RegistrationException("Age for registration must not be less than 18");
        }
        storageDao.add(user);
        return user;
    }
}
