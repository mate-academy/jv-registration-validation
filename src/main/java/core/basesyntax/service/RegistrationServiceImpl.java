package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        checkIfUserExist(user);
        storageDao.add(user);
        return user;
    }

    private void checkForNulls(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("User argument is null");
        }
        if (user.getLogin() == null) {
            throw new InvalidRegistrationDataException("User login is null");
        }
        if (user.getAge() == null) {
            throw new InvalidRegistrationDataException("User age is null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().equals("")) {
            throw new InvalidRegistrationDataException("Login is empty");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() > 116) {
            throw new InvalidRegistrationDataException("Age must be between 18 and 116");
        }
        if (user.getAge() < 18) {
            throw new InvalidRegistrationDataException("User age is less than 18");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidRegistrationDataException("User password is not valid");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidRegistrationDataException("User password is less than 6 symbols");
        }
    }

    private void checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationDataException("User already exist");
        }
    }
}
