package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_AND_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new RegisterException("User can not be null");
        }

        for (User a : Storage.people) {
            if (a.getLogin().equals(user.getLogin())) {
                throw new RegisterException("Login already exists! Enter another value");
            }
        }
        if (user.getLogin() == null) {
            throw new RegisterException("Login can not be null!");
        }
        if (user.getLogin().length() < MIN_PASSWORD_AND_LOGIN_LENGTH) {
            throw new RegisterException("Minimum login length is 6. Try again");
        }
        if (user.getPassword() == null) {
            throw new RegisterException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_AND_LOGIN_LENGTH) {
            throw new RegisterException("Password must have more than 6 characters!");
        }
        if (user.getAge() == null) {
            throw new RegisterException("Age can not be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterException("You are too young! Minimum age is 18 but you enter:"
                     + user.getAge());
        }
        return storageDao.add(user);
    }
}
