package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 18;
    private static final int MIN_NUMBER_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is invalid. Please enter the data");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The age is invalid");
        }
        if (user.getAge() < MAX_AGE) {
            throw new RegistrationException("The user must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with this login already exists");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The login is invalid");
        }
        if (user.getLogin().length() < MIN_NUMBER_CHARACTERS) {
            throw new RegistrationException("The login is short");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password is invalid");
        }
        if (user.getPassword().length() < MIN_NUMBER_CHARACTERS) {
            throw new RegistrationException("The password is short");
        }
        return storageDao.add(user);
    }
}
