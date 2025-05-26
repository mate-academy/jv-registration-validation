package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.NoValidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        Integer age = user.getAge();
        String login = user.getLogin();
        String password = user.getPassword();

        if (age == null || login == null || password == null) {
            throw new NoValidUserException("Input data is not valid");
        }
        if (age < 18) {
            throw new NoValidUserException("Age of user less then 18");
        }
        if (login.length() < 6) {
            throw new NoValidUserException("Login of user less then 6 characters");
        }
        if (password.length() < 6) {
            throw new NoValidUserException("Password of user less then 6 characters");
        }

        if (storageDao.get(login) == null) {
            storageDao.add(user);
            return user;
        }

        throw new NoValidUserException("A user with this login is "
                + "already registered in the system.");
    }
}
