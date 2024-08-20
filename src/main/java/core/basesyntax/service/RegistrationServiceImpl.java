package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login should be at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("Age should be at least 18 years old");
        }
        for (User current : Storage.people) {
            if (current.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with such login already exist");
            }
        }
        Storage.people.add(user);
        return user;
    }
}
