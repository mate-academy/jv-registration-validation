package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    static final int REQUIRED_AGE = 18;
    static final int REQUIRED_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getPassword() == null) {
            throw new RegistrationException("Error: password null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Error: login null!");
        }
        if (user.getAge() < REQUIRED_AGE) {
            throw new RegistrationException("Age should be "
                    + REQUIRED_AGE + " and cannot be less than zero!");
        }
        if (user.getPassword().length() < REQUIRED_SYMBOLS) {
            throw new RegistrationException("Password must be at least "
                    + REQUIRED_SYMBOLS + " symbols");
        }
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new RegistrationException("Maybe you forgot to fill in your login or password!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user already exists!");
        }
        return storageDao.add(user);
    }
}
