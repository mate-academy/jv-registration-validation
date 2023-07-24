package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

import static core.basesyntax.db.Storage.people;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_AND_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

    isAllUserFieldsNotNull(user);
    isUserNew(user);
    isLoginValid(user);
    isPasswordValid(user);
    isUserAlreadyGrownup(user);

    return storageDao.add(user);
    }

    private boolean isUserNew(User user) {
        if (people.contains(user)) {
            throw new RegistrationException("User already exist");
        }
        return true;
    }

    private boolean isLoginValid(User user) {
        if (user.getLogin().length() < MIN_LOGIN_AND_PASSWORD_LENGTH) {
            throw new RegistrationException("Login length is less than 6 symbols");
        }
        return true;
    }

    private boolean isPasswordValid(User user) {
        if (user.getPassword().length() < MIN_LOGIN_AND_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length is less than 6 symbols");
        }
        return true;
    }

    private boolean isUserAlreadyGrownup(User user) {
        if (user.getAge() < 18) {
            throw new RegistrationException("Sorry, here is link to Disney`s site - disney.com");
        }
        return true;
    }

    private boolean isAllUserFieldsNotNull(User user) {
        if (user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RegistrationException("Sorry, some field is Null");
        }
        return true;
    }
}

