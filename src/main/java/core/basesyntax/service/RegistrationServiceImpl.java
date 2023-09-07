package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN_LIMIT = 18;
    private static final int LOGIN_MIN_LIMIT = 6;
    private static final int PASSWORD_MIN_LIMIT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User`s data is empty");
        }
        checkUserExist(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkUserExist(User user) {
        if (Storage.PEOPLE.contains(user)) {
            throw new RegistrationException("User " + user.getLogin()
                    + " has already registered");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("User can`t registration because "
                    + "age field is empty");
        }
        if (user.getAge() < AGE_MIN_LIMIT) {
            throw new RegistrationException("User " + user.getAge()
                    + " can`t registration because user`s age - "
                    + user.getAge() + " - is less than " + AGE_MIN_LIMIT);
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User can`t registration because "
                    + "login field is empty");
        }
        if (user.getLogin().length() < LOGIN_MIN_LIMIT) {
            throw new RegistrationException("User " + user.getLogin()
                    + " can`t registration because user`s login - "
                    + user.getLogin() + " is short");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("User can`t registration because"
                    + "password field is empty");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LIMIT) {
            throw new RegistrationException("User " + user.getPassword()
                    + " can`t registration because user`s password - "
                    + user.getPassword() + " is short");
        }
    }
}
