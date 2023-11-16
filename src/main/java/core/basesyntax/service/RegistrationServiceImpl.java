package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AgeRestrictionException;
import core.basesyntax.exception.InputValueException;
import core.basesyntax.exception.LoginLengthException;
import core.basesyntax.exception.PasswordLengthException;
import core.basesyntax.exception.UserAlreadyExistsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserAlreadyExistsException, PasswordLengthException,
            LoginLengthException, InputValueException, AgeRestrictionException {
        if (user == null) {
            throw new InputValueException("Input cannot be null");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (user.getLogin() == null) {
            throw new InputValueException("Login cannot be null");
        } else if (user.getLogin().length() < MIN_LENGTH) {
            throw new LoginLengthException("Login must be longer than 6 characters");
        }

        if (user.getPassword() == null) {
            throw new InputValueException("Password cannot be null");
        } else if (user.getPassword().length() < MIN_LENGTH) {
            throw new PasswordLengthException("Password must be longer than 6 characters");
        }

        if (user.getAge() == 0) {
            throw new InputValueException("Age cannot be null");
        } else if (user.getAge() < 0) {
            throw new AgeRestrictionException("Age cannot be negative");
        } else if (user.getAge() <= MIN_AGE) {
            throw new AgeRestrictionException("You should be 18 or older. Please try later");
        }

        storageDao.add(user);
        return user;
    }
}
