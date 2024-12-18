package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User registeredUser : Storage.people) {
            if (registeredUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException(
                        "User with login " + user.getLogin() + " already exist");
            }
        }
        if (user.getLogin().length() < 6 || user.getLogin() == null) {
            throw new RegistrationException("User login should be at least 6 characters");
        }
        if (user.getPassword().length() < 6 || user.getPassword() == null) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RegistrationException("User age must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
