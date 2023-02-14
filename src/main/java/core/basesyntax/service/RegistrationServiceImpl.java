package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        storageDao.add(user);
        return user;
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Incorrect password!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Incorrect password!");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Incorrect user login!");
        }
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new RegistrationException("This login is not available!");
            }
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < 18) {
            throw new RegistrationException("Incorrect user age!");
        }
    }
}
