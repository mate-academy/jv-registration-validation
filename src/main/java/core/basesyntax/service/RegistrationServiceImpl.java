package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        for (User currentUser : Storage.people) {
            if (user.getLogin().equals(currentUser.getLogin())) {
                throw new RegistrationException("User with same login already exist");
            }
        }
        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Not enough characters in login: "
                    + user.getLogin()
                    + ". Min allowed characters is "
                    + MIN_CHARACTERS);
        }
        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Not enough characters in password: "
                    + user.getPassword()
                    + ". Min allowed characters is "
                    + MIN_CHARACTERS);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is "
                    + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
