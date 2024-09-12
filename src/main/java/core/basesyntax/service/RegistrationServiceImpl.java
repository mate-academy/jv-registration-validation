package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_PASSWORD_LENGTH = 6;
    private static final int DEFAULT_LOGIN_LENGTH = 6;
    private static final int DEFAULT_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RegistrationException("Cannot apply null value");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("User is already registered");
        }
        if (user.getAge() < DEFAULT_AGE) {
            throw new RegistrationException("Cannot register, user age is less than 18");
        }
        if (user.getLogin().length() < DEFAULT_LOGIN_LENGTH) {
            throw new RegistrationException("Cannot register, user login: \""
                    + user.getLogin() + "\" is less than 6 length");
        }
        if (user.getPassword().length() < DEFAULT_PASSWORD_LENGTH) {
            throw new RegistrationException("Cannot register, user password: \""
                    + user.getPassword() + "\" is less than 6 length");
        }
        if (user.getLogin().isBlank()) {
            throw new RegistrationException("Cannot register, user login cannot be blank");
        }
        if (user.getPassword().isBlank()) {
            throw new RegistrationException("Cannot register, user password cannot be blank");
        }

        return storageDao.add(user);
    }

}
