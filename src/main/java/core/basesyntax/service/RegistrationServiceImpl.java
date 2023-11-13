package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!!!");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Login cannot be null!!");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty line!!!");
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RegistrationException("Your password must contain at least "
                    + MIN_LENGTH_OF_PASSWORD + " characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("there is " + user.getLogin()
                    + " user already exist!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
