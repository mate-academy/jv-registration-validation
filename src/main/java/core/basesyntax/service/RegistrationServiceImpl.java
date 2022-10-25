package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be: " + user);
        }
        workWithLogin(user.getLogin());
        workWithPassword(user.getPassword());
        workWithAge(user.getAge());
        return storageDao.add(user);
    }

    private void workWithLogin(String login) {
        if (login == null) {
            throw new RuntimeException("User login can not be: " + login);
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException(String.format("User with login: %s already created",
                    login));
        }
    }

    private void workWithPassword(String password) {
        if (password == null) {
            throw new RuntimeException("User password can not be: " + password);
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password length shod be more or equals: "
                    + MIN_PASSWORD_LENGTH);
        }
    }

    private void workWithAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("User age can not be: " + age);
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("you are not of legal age");
        }
    }

}
