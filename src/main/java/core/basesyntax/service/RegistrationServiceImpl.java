package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InputDataException;
import core.basesyntax.exeption.LoginAlreadyExistsException;
import core.basesyntax.exeption.UserIsNullException;
import core.basesyntax.exeption.UsersAgeException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    /* TODO: delete
    there is no user with such login in the Storage yet
    user's login is at least 6 characters
    user's password is at least 6 characters
    user's age is at least 18 years old

    user == null*/

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("User must be not null");
        }
        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LENGTH
                || user.getPassword() == null
                || user.getPassword().length() < MIN_LENGTH) {
            throw new InputDataException("Incorrect login or password length");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginAlreadyExistsException("This login is already exists");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new UsersAgeException("Your age must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
