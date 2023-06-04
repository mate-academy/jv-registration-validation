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
        checkUser(user);
        checkAge(user);
        checkPassword(user);
        checkLogin_onNull(user);
        checkUserExsitence(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be zero!");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("The password is very short!!");
        }
    }

    private void checkLogin_onNull(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Incorrect user login!");
        }
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new RegistrationException("This login is not available!");
            }
        }
    }

    private void checkUserExsitence(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already registered");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("The user must be over 18!");
        }
    }
}
