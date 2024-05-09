package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ACCEPT_THIS_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        int age = user.getAge();

        if (login == null) {
            throw new RegisterException("This isn't login");
        }
        if (password == null) {
            throw new RegisterException("This isn't password");
        }
        if (age < ACCEPT_THIS_AGE) {
            throw new RegisterException("Your age is too low");
        }
        if (password.length() < MINIMUM_LENGTH) {
            throw new RegisterException("Password is too short");
        }
        if (login.length() < MINIMUM_LENGTH) {
            throw new RegisterException("Login is too short");
        }
        if (user.getLogin()
                .equals(storageDao.get(login).getLogin())) {
            throw new RegisterException("We have already this login");
        }

        return storageDao.add(user);
    }
}
