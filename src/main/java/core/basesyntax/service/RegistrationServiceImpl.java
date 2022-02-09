package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_SYMBOL = 7;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validUser(user);
        validAge(user);
        validPassword(user);
        validLogin(user);
        validEmpty(user);
        validSpaces(user);
        return storageDao.add(user);
    }

    private void validSpaces(User user) {
        if (user.getLogin().trim().contains(" ") || user.getPassword().trim().contains(" ")) {
            throw new RuntimeException("Spaces are not allowed. :(");
        }
    }

    private void validEmpty(User user) {
        if (user.getLogin().trim().equals("") || user.getPassword().trim().equals("")) {
            throw new RuntimeException("Empty data is not allowed. :(");
        }
    }

    private void validUser(User user) {
        if (user == null) {
            throw new RuntimeException("User does not exist. :(");
        }
    }

    private void validAge(User user) {
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("You must be older than 18 but younger than 100. :)");
        }
    }

    private void validPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_SYMBOL) {
            throw new RuntimeException("Password must has 7 characters or more. :)");
        }
    }

    private void validLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Enter login. :)");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such login already exist. :(");
        }
    }

}
