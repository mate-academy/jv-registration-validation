package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.DuplicateAnExistingLoginException;
import core.basesyntax.exceptions.NotCorrectAgeException;
import core.basesyntax.exceptions.NotCorrectPassword;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMAL_PASSWORD_SIZE = 6;
    private static final int MINIMAL_AGE = 18;

    @Override
    public User register(User user) {
        nullCheck(user);
        correctPasswordCheck(user);
        ageCheck(user);
        sameLoginCheck(user);
        storageDao.add(user);
        return user;
    }

    private void correctPasswordCheck(User user) {
        if (user.getPassword().length() < MINIMAL_PASSWORD_SIZE) {
            throw new NotCorrectPassword("Password to short");
        }
    }

    private void sameLoginCheck(User user) {
        User userFromStorage = storageDao.get(user.getLogin());
        if (userFromStorage != null) {
            throw new DuplicateAnExistingLoginException("This email "
                    + user.getLogin() + " has already been taken");
        }

    }

    private void ageCheck(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new NotCorrectAgeException("Ure are to young. Age: " + user.getAge());
        }
    }

    private void nullCheck(User user) {
        if (user == null || user.getAge() == null ||
                user.getLogin() == null || user.getPassword() == null) {
            throw new NullPointerException("Password or login can not be null");
        }
    }
}
