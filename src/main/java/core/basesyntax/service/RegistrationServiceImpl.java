package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        checkDuplicate(user);
        return storageDao.add(user);
    }

    private void checkDuplicate(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with email= "
                    + user.getLogin() + " already exists");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new InvalidDataException("Your login is too short");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User too young for our service");
        } else if (user.getAge() < 0) {
            throw new InvalidDataException("User cannot be younger than 0");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidDataException("Your password is too short");
        }
    }
}
