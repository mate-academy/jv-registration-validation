package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (checkUser(user)) {
            return storageDao.add(user);
        }
        return null;
    }

    private boolean checkUser(User user) {
        return isValidUserLogin(user) && isValidUserAge(user) && isValidUserPassword(user);
    }

    private boolean isValidUserLogin(User user) {
        if (user.getLogin() != null && storageDao.get(user.getLogin()) == null
                && isWhitespaceLine(user.getLogin())) {
            return true;
        } else {
            throw new RuntimeException("Not valid name for this user");
        }
    }

    private boolean isValidUserAge(User user) {
        if (user.getAge() != null && user.getAge() >= USER_MIN_AGE) {
            return true;
        } else {
            throw new RuntimeException("Not valid age for this user");
        }
    }

    private boolean isValidUserPassword(User user) {
        if (user.getPassword() != null
                && user.getPassword().length() >= PASSWORD_MIN_LENGTH
                && isWhitespaceLine(user.getPassword())) {
            return true;
        } else {
            throw new RuntimeException("Not valid password, "
                    + "password must be less then 6 characters");
        }
    }

    private boolean isWhitespaceLine(String line) {
        return line.trim().length() > 0;
    }

}
