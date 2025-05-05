package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUM_OF_CHARS_IN_LOGIN = 6;
    private static final int MIN_NUM_OF_CHARS_IN_PASSWORD = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidUserDataException {
        if (user == null) {
            throw new InvalidUserDataException(
                    "User can't be null"
            );
        }
        if (user.getLogin() == null) {
            throw new InvalidUserDataException(
                    "Login can't be null"
            );
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException(
                    "Password can't be null"
            );
        }
        if (user.getAge() == null) {
            throw new InvalidUserDataException(
                    "User's age can't be null"
            );
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "User with login "
                    + user.getLogin()
                    + " already exists"
            );
        }
        if (user.getLogin().length() < MIN_NUM_OF_CHARS_IN_LOGIN) {
            throw new InvalidUserDataException(
                    "User's login length is: " + user.getLogin().length()
                            + ". Min allowed login length is " + MIN_NUM_OF_CHARS_IN_LOGIN
            );
        }
        if (user.getPassword().length() < MIN_NUM_OF_CHARS_IN_PASSWORD) {
            throw new InvalidUserDataException(
                    "User's password length is: " + user.getPassword().length()
                            + ". Min allowed password length is " + MIN_NUM_OF_CHARS_IN_PASSWORD
            );
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidUserDataException(
                   "Invalid age: " + user.getAge() + ". Min allowed age is " + MIN_USER_AGE
            );
        }
        return storageDao.add(user);
    }
}
