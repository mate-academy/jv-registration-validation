package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import exceptions.RegistrationException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 150;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNull(user);
        return (isValidValue(user)
                && storageDao.get(user.getLogin()) == null) ? storageDao.add(user) : null;
    }

    public void checkForNull(User user) {
        if (user != null) {
            if (user.getLogin() == null) {
                throw new RegistrationException("Login can't be null!");
            }
            if (user.getPassword() == null) {
                throw new RegistrationException("Password can't be null!");
            }
            if (user.getAge() == null) {
                throw new RegistrationException("Age can't be null!");
            }
            return;
        }
        throw new RegistrationException("Object of user can't be null!");
    }

    public boolean isValidValue(User user) {
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login should have at least 6 characters, not "
                    + user.getLogin().length());
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password should have at least 6 characters, not "
                    + user.getPassword().length());
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Allowed age is min = " + MIN_AGE + ", max =" + MAX_AGE);
        }
        return checkCorrectLogin(user.getLogin());
    }

    public boolean checkCorrectLogin(String input) {
        for (char symbol : input.toCharArray()) {
            if (Character.UnicodeBlock.of(symbol) == Character.UnicodeBlock.CYRILLIC
                    || (!Character.isLetterOrDigit(symbol) && symbol != '_')
                    || Character.isSpaceChar(symbol)
                    || Character.isWhitespace(symbol)) {
                return false;
            }
        }
        return true;
    }
}
