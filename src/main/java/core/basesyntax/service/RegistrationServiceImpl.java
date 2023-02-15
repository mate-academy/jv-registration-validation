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
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        validLogin(user.getLogin());
        storageDao.add(user);
        return user;
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Incorrect password!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Incorrect password!");
        }
    }

    private void checkLogin(User user) {
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new RegistrationException("This login is not available!");
            }
        }
    }

    private void validLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Incorrect user login!");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Incorrect user age!");
        }
    }
}
