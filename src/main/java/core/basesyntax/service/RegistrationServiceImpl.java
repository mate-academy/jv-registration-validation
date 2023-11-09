package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int minAge = 18;

    @Override
    public User register(User user) throws RegistrationException {

        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        if (user.getAge() < minAge) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + minAge);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login was already taken");
        }
        return storageDao.add(user);
    }
}
