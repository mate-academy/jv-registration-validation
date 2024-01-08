package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SYMBOL = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validate(user);
        return storageDao.add(user);
    }

    private void validate(User user) {
        if (user == null) {
            throw new BadDataValidationException("User is null!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new BadDataValidationException("Not valid age: " + user.getAge()
                    + ". Min age is 18!");
        }
        if (user.getPassword() == null || user.getPassword().contains(" ")
                || user.getPassword().length() < MIN_SYMBOL) {
            throw new BadDataValidationException("Password is invalid!");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")
                || user.getLogin().length() < MIN_SYMBOL) {
            throw new BadDataValidationException("Login " + user.getLogin() + " is invalid!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new BadDataValidationException("User with login " + user.getLogin()
                    + " is already in the storage.");
        }
    }
}
