package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_SIZE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        // user checking
        if (user == null) {
            throw new InvalidUserDataException("can't register user with"
                    + "null object");
        }
        // password checking
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("can't register user with"
                    + "null password");
        }
        if (user.getPassword().length() < MIN_PASS_SIZE) {
            throw new InvalidUserDataException("can't register user with"
                    + "invalid password. Minimum password length " + MIN_PASS_SIZE
                    + " characters");
        }
        // age checking
        if (user.getAge() == null) {
            throw new InvalidUserDataException("can't register user with"
                    + "null age");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("can't register user with"
                    + "invalid age. Minimum age for registration is "
                    + MIN_AGE + " years old");
        }
        // login checking
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("can't register user with"
                    + "null login");
        }
        for (User existingUser : Storage.people) {
            if (existingUser.getLogin().equals(user.getLogin())) {
                throw new InvalidUserDataException("can't register user with"
                        + "this login");
            }
        }
        storageDao.add(user);
        return user;
    }
}
