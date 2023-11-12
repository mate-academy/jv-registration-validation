package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exaption.RegistrationException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        existLoginValid(user);
        passwordLengthValid(user);
        loginLengthValid(user);
        ageValid(user);
        storageDao.add(user);
        return user;
    }

    private void existLoginValid(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is register");
        }
    }

    private void passwordLengthValid(User user) {
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("User with this password is less than 6");
        }
    }

    private void loginLengthValid(User user) {
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("User with this login is less than 6");
        }
    }

    private void ageValid(User user) {
        if (user.getAge() < 18) {
            throw new RegistrationException("User age less than 18");
        }
    }

    private void checkNull(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("User info can't be null");
        }
    }
}
