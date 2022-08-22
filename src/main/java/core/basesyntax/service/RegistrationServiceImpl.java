package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login: " + user.getLogin()
                    + " already exists!");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null!");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can't contain empty space or be empty!");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new RuntimeException("Login need to begin with letter: " + user.getLogin() + "!");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be 6 - 20 characters. "
                    + "Your password is invalid: " + user.getPassword());
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null.");
        }
        if (user.getAge() < 0) {
            throw new RuntimeException("Can't enter age less than 0." + user.getAge());
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You are under age! Forbidden to register."
                    + " Come back when you are 18.");
        }
    }
}
