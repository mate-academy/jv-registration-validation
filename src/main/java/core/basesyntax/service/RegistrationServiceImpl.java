package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.WrongValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOW_AGE_LIMIT = 18;
    private static final int LOW_LOGIN_LIMIT = 6;
    private static final int LOW_PASS_LIMIT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new WrongValidationException("User`s data is empty");
        }
        checkUserExist(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkUserExist(User user) {
        if (Storage.PEOPLE.contains(user)) {
            throw new WrongValidationException("User " + user.getLogin()
                    + " has already registered");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new WrongValidationException("User can`t registration because "
                    + "age field is empty");
        }
        if (user.getAge() < LOW_AGE_LIMIT) {
            throw new WrongValidationException("User " + user.getAge()
                    + " can`t registration because user`s age - "
                    + user.getAge() + " - is less than 18");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new WrongValidationException("User can`t registration because "
                    + "login field is empty");
        }
        if (user.getLogin().length() < LOW_LOGIN_LIMIT) {
            throw new WrongValidationException("User " + user.getLogin()
                    + " can`t registration because user`s login - "
                    + user.getLogin() + " is short");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new WrongValidationException("User can`t registration because"
                    + "password field is empty");
        }
        if (user.getPassword().length() < LOW_PASS_LIMIT) {
            throw new WrongValidationException("User " + user.getPassword()
                    + " can`t registration because user`s password - "
                    + user.getPassword() + " is short");
        }
    }
}
