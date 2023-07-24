package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int minAge = 18;
    private final int minLength = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getAge() == null || user.getAge() < minAge) {
            throw new RegistrationException("Invalid user age");
        }
        if (user.getLogin() == null || user.getLogin().length() < minLength) {
            throw new RegistrationException("Invalid user login " + user.getLogin()
                    + ". Login must be bigger than " + minLength + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < minLength) {
            throw new RegistrationException("Invalid user password " + user.getPassword()
                    + ". Password must be bigger than " + minLength + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already exist");
        }
        return storageDao.add(user);
    }
}
