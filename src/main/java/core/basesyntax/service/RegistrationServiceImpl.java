package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_PASS_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        // user checking
        if (user == null) {
            throw new NullPointerException("can't register user with"
                    + "null object");
        }
        // password checking
        if (user.getPassword() == null) {
            throw new NullPointerException("can't register user with"
                    + "null password");
        }
        if (user.getPassword().length() < MIN_PASS_SIZE) {
            throw new InvalidUserDataException();
        }
        // age checking
        if (user.getAge() == null) {
            throw new NullPointerException("can't register user with"
                    + "null age");
        }
        if (user.getAge() < 18) {
            throw new InvalidUserDataException();
        }
        // login checking
        if (user.getLogin() == null) {
            throw new NullPointerException("can't register user with"
                    + "null login");
        }
        for (User existingUser : Storage.people) {
            if (existingUser.getLogin().equals(user.getLogin())) {
                throw new InvalidUserDataException();
            }
        }
        storageDao.add(user);
        return user;
    }
}
