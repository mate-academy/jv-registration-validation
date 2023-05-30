package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_USER_NAME_LENGTH = 6;
    private static final String REGEX_PASSWORD = "^.{6,}$";
    private static final Pattern PATTERN_PASSWORD = Pattern.compile(REGEX_PASSWORD);
    private static final String REGEX_LOGIN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern PATTERN_LOGIN = Pattern.compile(REGEX_LOGIN);
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isValidData(user)
                && isValidEmail(user)
                && isValidPassword(user)
                && !isUserWithSuchLoginExists(user)) {
            return storageDao.add(user);
        }

        return null;
    }

    private boolean isValidData(User user) throws RegistrationException {
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new RegistrationException("Login can't be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new RegistrationException("Password can't be null or empty");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return true;
    }

    private boolean isValidPassword(User user) {
        String password = user.getPassword();
        Matcher matcher = PATTERN_PASSWORD.matcher(password);
        return matcher.matches();
    }

    private boolean isValidEmail(User user) {
        String login = user.getLogin();
        String userName = login.substring(0, login.indexOf("@"));
        if (userName.length() < MIN_USER_NAME_LENGTH
                || !Character.isLetterOrDigit(userName.charAt(0))) {
            return false;
        }
        Matcher matcher = PATTERN_LOGIN.matcher(login);
        return matcher.matches();
    }

    private boolean isUserWithSuchLoginExists(User user) {
        String userLogin = user.getLogin();
        User userPresent = storageDao.get(userLogin);
        if (userPresent == null) {
            return false;
        }
        if (!userPresent.getLogin().equals(user.getLogin())) {
            throw new RegistrationException("А user with this login already exists");
        }
        return true;
    }
}
