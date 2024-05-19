package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.IncorrectInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LEN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IncorrectInputDataException("Fill the fields before registration");
        }
        validateAge(user.getAge());
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void validateAge(Integer age) {
        if (age == null ) {
            throw new IncorrectInputDataException("Age field is empty");
        }
        if (age < MIN_AGE) {
            throw new IncorrectInputDataException("Age must be at least 18 ");
        }
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new IncorrectInputDataException("Login field is empty");
        }
        if ( login.length() < MIN_LEN) {
            throw new IncorrectInputDataException("Login too short");
        }
        if (storageDao.get(login) != null) {
            throw new IncorrectInputDataException("User with this login already exists");
        }

    }

    private void validatePassword(String password) {
        if (password == null ) {
            throw new IncorrectInputDataException("Password field is empty");
        }
        if (password.length() < MIN_LEN) {
            throw new IncorrectInputDataException("Password too short");
        }
    }
}
