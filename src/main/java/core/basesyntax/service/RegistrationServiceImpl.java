package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LEN_PAS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new RegistrationException("No user!");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("No Login!");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You are too young");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_LEN_PAS) {
            throw new RegistrationException("The password is week");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exist");
        }
        return storageDao.add(user);
    }
}
