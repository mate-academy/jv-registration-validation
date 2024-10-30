package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MINIMAL_AGE);
        }

        if (((people.contains(user.getLogin())
                && user.getLogin().length() >= MINIMAL_LENGTH)
                && user.getPassword().length() >= MINIMAL_LENGTH
                && user.getAge() >= MINIMAL_AGE)) {
            storageDao.add(user);
        }
        return user;
    }
}
