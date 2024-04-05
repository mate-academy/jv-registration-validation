package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNull(user);
        checkUserLogin(user);
        checkUserPassword(user);
        checkUserAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserException("Login is null");
        }

        String login = user.getLogin();
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserException("Login " + login + " is already taken.");
        }
        if (user.getLogin().length() < 6) {
            throw new UserException("Login " + login + " less then 6");
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new UserException("Password is null");
        }
        if (user.getPassword().length() < 6) {
            throw new UserException("Password " + user.getPassword() + " less then 6");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new UserException("User age is null");
        }
        if (user.getAge() < 18) {
            throw new UserException("User age " + user.getAge() + " less then 18");
        }
    }

    private void checkUserNull(User user) {
        if (user == null) {
            throw new UserException("User is null");
        }
    }
}
