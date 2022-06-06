package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    /*
        there is no user with such login in the Storage yet
        the user is at least 18 years old
        user password is at least 6 characters
    */
    @Override
    public User register(User user) {
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Password should have 6 and more characters");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User should be at least 18 y.o.");
        }
        if (!(user.getLogin() == null || user.getLogin().length() < 4)) {
            for (User registeredUser : Storage.people) {
                if (registeredUser.getLogin().equals(user.getLogin())) {
                    throw new RuntimeException("User with such login exist already");
                }
            }
        } else {
            throw new RuntimeException("User's LOGIN should be more than 4 characters");
        }
        storageDao.add(user);
        return user;
    }
}
