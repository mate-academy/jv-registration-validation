package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        isUserValid(user);
        return storageDao.add(user);
    }

    private void isUserValid(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("This user is already existed in the Storage!");
        }
        isLoginValid(user.getLogin());
        isPasswordValid(user.getPassword());
        isAgeValid(user.getAge());
    }

    private void isLoginValid(String login) {
        if (login == null) {
            throw new RegistrationException("User's login can't be null!");
        }
        if (login.isBlank()) {
            throw new RegistrationException("User's login can't consist only white spaces!");
        }
        if (login.length() < MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("User's login should be longer than "
                    + MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS + "!");
        }
    }

    private void isPasswordValid(String password) {
        if (password == null) {
            throw new RegistrationException("User's password can't be null!");
        }
        if (password.isBlank()) {
            throw new RegistrationException("User's password can't consist only white spaces");
        }
        if (password.length() < MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("User's password should be longer than "
                    + MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS + "!");
        }
    }

    private void isAgeValid(Integer age) {
        if (age == null) {
            throw new RegistrationException("User's age can't be null!");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("User can't be registered with less age than min: "
                    + MIN_AGE + "!");
        }
    }
}
