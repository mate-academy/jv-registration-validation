package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_USER_NAME_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    private boolean validData(User user) throws RegistrationException {
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

    private boolean validPassword(User user) {
        String password = user.getPassword();
        String regex = "^.{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validEmail(User user) {
        String login = user.getLogin();
        String userName = login.substring(0, login.indexOf("@"));
        if (userName.length() < MIN_USER_NAME_LENGTH
                || !Character.isLetterOrDigit(userName.charAt(0))) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    private boolean noUserWithSuchLogin(User user) {
        String userLogin = user.getLogin();
        User userPresent = storageDao.get(userLogin);
        if (userPresent == null) {
            return false;
        }
        return userPresent.getLogin().equals(user.getLogin());
    }

    @Override
    public User register(User user) {
        if (validData(user)
                && validEmail(user)
                && validPassword(user)
                && !noUserWithSuchLogin(user)) {
            return storageDao.add(user);
        }

        return null;
    }
}
