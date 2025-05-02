package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Your login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Your password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Your age can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Your login should be at least "
                    + MIN_LENGTH + " characters long");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Your password should be at least "
                    + MIN_LENGTH + " characters long");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Your age should be " + MIN_AGE + " or older");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }
        return storageDao.add(user);
    }
}
