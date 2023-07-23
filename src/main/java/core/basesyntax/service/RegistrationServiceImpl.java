package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AlreadyRegisteredException;
import core.basesyntax.exceptions.ValidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws AlreadyRegisteredException, ValidDataException {
        checkNulls(user);
        isLoginNotRegistered(user);
        isIdNotRegistered(user);
        isPasswordLoginLengthValid(user);
        isPasswordValid(user);
        isLoginValid(user);
        isAgeValid(user);
        return storageDao.add(user);
    }

    private boolean checkNulls(User user) throws ValidDataException {
        // Null login check
        if (user.getLogin() == null) {
            throw new ValidDataException("You did`t fill login field!");
        }
        // Null password check
        if (user.getPassword() == null) {
            throw new ValidDataException("You did`t fill password field!");
        }
        // Null age check
        if (user.getAge() == null) {
            throw new ValidDataException("You did`t fill age field!");
        }
        return true;
    }

    private boolean isLoginNotRegistered(User user) throws AlreadyRegisteredException {
        // Same login check
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new AlreadyRegisteredException("Same login already registered");
        }
        return true;
    }

    private boolean isIdNotRegistered(User user) throws AlreadyRegisteredException {
        // Same id check
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getId() != null
                && storageDao.get(user.getLogin()).getId().equals(user.getId())) {
            throw new AlreadyRegisteredException("Same id already registered");
        }
        return true;
    }

    private boolean isPasswordLoginLengthValid(User user) throws ValidDataException {
        // Additional validation for password and login
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new ValidDataException("Password must be at least 6 characters long.");
        }
        return true;
    }

    private boolean isPasswordValid(User user) throws ValidDataException {
        for (Character character : user.getPassword().toCharArray()) {
            if (Character.isLetter(character) || Character.isDigit(character)) {
                continue;
            }
            throw new ValidDataException("Password is not meets A-Z-a-z-1-9");
        }
        return true;
    }

    private boolean isLoginValid(User user) throws ValidDataException {
        for (Character character : user.getLogin().toCharArray()) {
            if (Character.isLetter(character) || Character.isDigit(character)) {
                continue;
            }
            throw new ValidDataException("Login is not meets A-Z-a-z-1-9");
        }
        return true;
    }

    private boolean isAgeValid(User user) throws ValidDataException {
        if (user.getAge() < 18) {
            throw new ValidDataException("Your age is not acceptable. "
                    + "Come here again after " + (18 - user.getAge())
                    + " year/s \n We will wait for you ;)");
        }
        return true;
    }
}
