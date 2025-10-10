package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Constants;
import core.basesyntax.db.Storage;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("User is already in database");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters long");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age can't be null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + Constants.MIN_AGE);
        }
        return storageDao.add(user);
    }
}
