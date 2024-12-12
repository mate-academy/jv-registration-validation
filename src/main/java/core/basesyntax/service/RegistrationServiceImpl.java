package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already used.");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login should contains at least 6 symbols.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password should contains at least 6 symbols.");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Users under 18 years are not approved.");
        }
        storageDao.add(user);
        return user;
    }
}
