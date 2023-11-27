package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!isLoginValid(user.getLogin())) {
            throw new RegistrationException("Login should have at least 6 characters!");
        }
        if (!isPasswordValid(user.getPassword())) {
            throw new RegistrationException("Password should have at least 6 characters!");
        }
        if (!isAdult(user.getAge())) {
            throw new RegistrationException("You must be over 18 to register");
        }
        for (User userInStorage : Storage.getPeople()) {
            if (user.getLogin().equals(userInStorage.getLogin())) {
                throw new RegistrationException("User already exists!");
            }
        }
        return storageDao.add(user);
    }

    @Override
    public void removeAllUsers() {
        storageDao.removeAll();
    }

    @Override
    public boolean isLoginValid(String login) {
        return login != null && login.length() >= 6;
    }

    @Override
    public boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    @Override
    public boolean isAdult(int age) {
        return age >= 18;
    }

}
