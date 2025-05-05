package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6 ) {
            throw new RegistrationException("User login has less than six characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("User password has less than six characters");
        }
        if (user.getAge() <18) {
            throw new RegistrationException("The user's age is less than eighteen");
        }

        return null;
    }
}
