package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        checkLogin(user.getLogin());
        storageDao.add(user);
        return user;
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Incorrect password!");
        }
        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Incorrect password!");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Incorrect user login!");
        }
        for (User person : Storage.people) {
            if (login.equals(person.getLogin())) {
                throw new RegistrationException("This login is not available!");
            }
        }
    }

    private void checkAge(Integer age) {
        if (age < MINIMAL_AGE) {
            throw new RegistrationException("Incorrect user age!");
        }
    }
}
