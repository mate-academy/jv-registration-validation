package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login exists");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cant be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cant be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cant be null");
        }

        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Length of password should be more than "
                    + MIN_CHARACTERS + " characters");
        }

        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Length of login should be more than "
                    + MIN_CHARACTERS + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                   + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Age should hav positive value");
        }
        storageDao.add(user);
        return user;
    }
}
