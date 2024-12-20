package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;

    @Override
    public User register(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("User is null");
        }
        if (!isAgeValid(user.getAge())) {
            throw new InvalidUserException("User age is invalid");
        }
        if (!isLoginValid(user.getLogin())) {
            throw new InvalidUserException("User login is invalid");
        }
        if (!isPasswordValid(user.getPassword())) {
            throw new InvalidUserException("User is null");
        }
        if (!isLoginUnique(user.getLogin())) {
            throw new InvalidUserException("User is null");
        }
        storageDao.add(user);
        return user;
    }

    private boolean isLoginValid(String login) {
        return login != null && login.length() >= MINIMUM_LENGTH;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.length() >= MINIMUM_LENGTH;
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age >= MINIMUM_AGE;
    }

    private boolean isLoginUnique(String login) {
        return storageDao.get(login) == null;
    }
}
