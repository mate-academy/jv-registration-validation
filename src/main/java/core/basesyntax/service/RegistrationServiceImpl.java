package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_AND_PASSWORD_MINIMUM = 6;
    private static final int AGE_MINIMUM = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidUserException {

        if (user.getAge() == null) {
            throw new InvalidUserException("User`s age can`t be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("User`s password can`t be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserException("User`s login can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User already in storage!");
        }
        if (user.getAge() < AGE_MINIMUM) {
            throw new InvalidUserException("Invalid user`s age: " + user.getAge()
            + "Min allowed age is: " + AGE_MINIMUM);
        }
        if (user.getLogin().length() < LOGIN_AND_PASSWORD_MINIMUM) {
            throw new InvalidUserException("Invalid login: " + user.getLogin()
                    + "Min allowed login length is: " + LOGIN_AND_PASSWORD_MINIMUM);
        }
        if (user.getPassword().length() < LOGIN_AND_PASSWORD_MINIMUM) {
            throw new InvalidUserException("Invalid password: " + user.getPassword()
                    + "Min allowed password length is: " + LOGIN_AND_PASSWORD_MINIMUM);
        }

        return storageDao.add(user);
    }
}
