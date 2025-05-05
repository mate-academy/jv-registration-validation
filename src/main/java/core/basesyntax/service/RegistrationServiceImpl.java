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
            throw new RegistrationException("the minimum age of the user must be: " + MAX_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with this login already exists");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The login is invalid");
        }
        if (user.getLogin().length() < MIN_NUMBER_CHARACTERS) {
            throw new RegistrationException("The minimum length of the login must be: "
                    + MIN_NUMBER_CHARACTERS);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password is invalid");
        }
        if (user.getPassword().length() < MIN_NUMBER_CHARACTERS) {
            throw new RegistrationException("The minimum length of the password must be: "
                    + MIN_NUMBER_CHARACTERS);
        }
        return storageDao.add(user);
    }
}
