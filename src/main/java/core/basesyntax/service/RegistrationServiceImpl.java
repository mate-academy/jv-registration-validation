package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.DuplicateAnExistingLoginException;
import core.basesyntax.exceptions.NotCorrectAgeException;
import core.basesyntax.exceptions.NotCorrectPassword;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

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
        if (user.getPassword().length() <= 6) {
            throw new NotCorrectPassword("Password to short");
        }
    }

    private void sameLoginCheck(User user) {
        User login = storageDao.get(user.getLogin());
        if (login != null) {
            throw new DuplicateAnExistingLoginException("This email "
                    + user.getLogin() + " has already been taken");
        }

    }

    private void ageCheck(User user) {
        if (user.getAge() < 18) {
            throw new NotCorrectAgeException("Ure are to young");
        }
    }

    private void nullCheck(User user) {
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new NullPointerException("Password or login can not be null");
        }
    }
}
