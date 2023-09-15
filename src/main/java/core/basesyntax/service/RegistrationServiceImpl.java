package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationExseption("This login already exist");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationExseption("Too short login, minimum 6 characters");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationExseption("Too short password, minimum 6 characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationExseption("Too small age, minimum 18 years");
        }
    }

    private void checkNull(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationExseption("Input data!!!");
        }
    }
}
