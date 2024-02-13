package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login is null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password is null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already in the storage");
        }
        if (user.getLogin().length() >= 6 && user.getPassword().length() >= 6 && user.getAge() >= 18) {
            storageDao.add(user);
            return user;
        } else {
            throw new RegistrationException("Wrong user data");
        }
    }
}
