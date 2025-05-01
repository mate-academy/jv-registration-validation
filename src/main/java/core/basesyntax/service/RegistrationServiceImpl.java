package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User Login can`t be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User Password can`t be null");
        }
        for (User currentUser : Storage.people) {
            if (user.getLogin().equals(currentUser.getLogin())) {
                throw new RegistrationException("User with such login "
                        + user.getLogin() + " exist in the Storage yet");
            }
        }
        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegistrationException("User's login is at least "
                    + MIN_CHARACTERS + " characters");
        }
        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegistrationException("User's password is at least "
                    + MIN_CHARACTERS + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age is at least "
                    + MIN_AGE + " years old");
        }
        storageDao = new StorageDaoImpl();
        return storageDao.add(user);
    }
}
