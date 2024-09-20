package core.basesyntax.service;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationValidationException("User cannot be null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationValidationException("Login must contain at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new
                    RegistrationValidationException("Password must contain at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationValidationException("Age does not meet the condition");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidationException("Login already exists");
        }
        return storageDao.add(user);
    }

    @Override
    public User get(String login) {
        return storageDao.get(login);
    }
}
