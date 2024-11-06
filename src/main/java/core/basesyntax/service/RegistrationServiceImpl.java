package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private static final Integer MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exist");
        }
        if (user.getLogin().length() <= MIN_CHARACTERS) {
            throw new RegistrationException("Login is too short, it should have at least: "
                    + MIN_CHARACTERS + " characters");
        }
        if (user.getPassword().length() <= MIN_CHARACTERS) {
            throw new RegistrationException("Password is too short, it should have at least: "
                    + MIN_CHARACTERS + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
