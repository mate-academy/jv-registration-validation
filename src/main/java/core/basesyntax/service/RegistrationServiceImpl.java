package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int ADULT_AGE = 18;
    public static final int MAX_AGE = 120;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_AND_LOGIN_LENGTH = 100;
    public static final int MIN_LOGIN_LENGTH = 2;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override

    public User register(User user) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        checkLogin(user.getLogin());
        checkUserInSystem(user);
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkUserInSystem(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Users : " + user.getLogin()
                    + " , can't register in system." + System.lineSeparator()
                    + "Because user with this login is already registered.");
        }
    }

    private void checkLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH
                || login.length() > MAX_PASSWORD_AND_LOGIN_LENGTH) {
            throw new InvalidDataException("Users : " + login
                    + " , can't register in system." + System.lineSeparator()
                    + "Login must contain attlist " + MIN_PASSWORD_LENGTH
                    + " symbols and less then " + MAX_PASSWORD_AND_LOGIN_LENGTH);
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH
                || password.length() > MAX_PASSWORD_AND_LOGIN_LENGTH) {
            throw new InvalidDataException("Users password : " + password
                    + " , can't register in system." + System.lineSeparator()
                    + "Password must contain attlist " + MIN_PASSWORD_LENGTH
                    + " symbols and less then " + MAX_PASSWORD_AND_LOGIN_LENGTH);
        }
    }

    private void checkAge(Integer age) {
        if (age == null || age < ADULT_AGE || age >= MAX_AGE) {
            throw new InvalidDataException("Users with: " + age + ", can't register in system."
                    + System.lineSeparator() + "Age must be at list "
                    + ADULT_AGE + " years and less then " + MAX_AGE);
        }
    }
}
