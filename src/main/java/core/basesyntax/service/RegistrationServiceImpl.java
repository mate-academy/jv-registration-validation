package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("The user does not exist");
        }
        isUserDataNull(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with this login already exists");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException("User's login cannot be less than "
                    + MIN_LENGTH_LOGIN + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age cannot be less than "
                    + MIN_AGE + " years old");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("User's password cannot be less than "
                    + MIN_LENGTH_PASSWORD + " characters");
        }
        storageDao.add(user);
        return user;
    }

    private void isUserDataNull(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
    }
}
