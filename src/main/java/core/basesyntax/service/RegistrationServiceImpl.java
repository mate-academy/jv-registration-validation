package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.throwable.InvalidDataEntry;

public class RegistrationServiceImpl implements RegistrationService {

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataEntry("User is null.");
        }
        userExistCheck(user.getLogin());
        loginSizeCheck(user.getLogin());
        ageCheck(user.getAge());
        passwordSizeCheck(user.getPassword());
        storageDao.add(user);
        return user;
    }

    @Override
    public boolean userExistCheck(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidDataEntry("User already exist.");
        }
        return true;
    }

    @Override
    public boolean loginSizeCheck(String login) {
        if (login.length() < 6) {
            throw new InvalidDataEntry("Login must have at least 6 characters.");
        }
        return true;
    }

    @Override
    public boolean passwordSizeCheck(String password) {
        if (password.length() < 6) {
            throw new InvalidDataEntry("Password must have at least 6 characters.");
        }
        return true;
    }

    @Override
    public boolean ageCheck(int age) {
        if (age < 18) {
            throw new InvalidDataEntry("Age must be greater than 18.");
        }
        return true;
    }
}
