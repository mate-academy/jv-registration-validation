package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    static final int MIN_AGE = 18;
    static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void register(User user) throws RegistrationException {
        if (user.getPassword() == null) {
            throw new RegistrationException("Error: password null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Error: login null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age should be "
                    + MIN_AGE + " and cannot be less than zero!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Maybe you forgot to fill in your login!");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException("Maybe you forgot to fill in your password!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user already exists!");
        }
        storageDao.add(user);
    }
}
