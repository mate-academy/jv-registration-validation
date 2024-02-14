package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.DbContainsSuchUserException;
import core.basesyntax.exception.UserEmptyOrNullLoginException;
import core.basesyntax.exception.UserIsNullException;
import core.basesyntax.exception.UserLoginLengthException;
import core.basesyntax.exception.UserMinimumAgeException;
import core.basesyntax.exception.UserNullAgeException;
import core.basesyntax.exception.UserNullOrEmptyPasswordException;
import core.basesyntax.exception.UserPasswordLengthException;
import core.basesyntax.exception.UserTooOldAgeException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("The user that was passed in argument equals null."
                    + " Registration is not possible");
        }
        if ((user.getLogin() == null || user.getLogin().length() == 0)) {
            throw new UserEmptyOrNullLoginException("The user that was passed in argument "
                    + "has not login. Registration is not possible");
        }
        if (user.getLogin().length() < 6) {
            throw new UserLoginLengthException("The user " + user + " that was passed in "
                    + "argument has name less than 6 characters. Registration is not possible");
        }
        if (user.getPassword() == null) {
            throw new UserNullOrEmptyPasswordException("The user that was passed in argument "
                    + "has not password. Registration is not possible");
        }
        if (user.getPassword().equals("")) {
            throw new UserNullOrEmptyPasswordException("The user that was passed in argument "
                    + "has empty password. Registration is not possible");
        }
        if (user.getPassword().length() < 6) {
            throw new UserPasswordLengthException("The user that was passed in argument "
                    + "has password less than 6 characters. Registration is not possible");
        }
        if (user.getAge() == null) {
            throw new UserNullAgeException("The users age can not be null"
                    + " Registration is not possible");
        }
        if (user.getAge() > 120) {
            throw new UserTooOldAgeException("The user that was passed in argument has too big age."
                    + " Registration is not possible");
        }
        if (user.getAge() < 18) {
            if (user.getAge() >= 0 && user.getAge() < 18) {
                throw new UserMinimumAgeException("The user must be at least 18 years of age."
                        + " Registration is not possible");
            }
            if (user.getAge() < 0) {
                throw new UserMinimumAgeException("You can not set the user a negative age value."
                        + " Registration is not possible");
            }
        }
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).equals(user)) {
            throw new DbContainsSuchUserException("The data base contains the user " + user
                    + " that was passed in argument yet. Registration is not possible");
        }
        return storageDao.add(user);
    }
}
