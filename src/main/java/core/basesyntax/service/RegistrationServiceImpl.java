package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("All fields must be specified.");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("The login must be specified.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The password must be specified.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The age must be specified.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("The user's age must be at least " + MIN_AGE + ".");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already registered.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("The user's password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
        return storageDao.add(user);
    }
}
