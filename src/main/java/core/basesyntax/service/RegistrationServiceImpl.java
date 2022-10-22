package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age " + user.getAge() + " less than " + MIN_AGE);
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Invalid pass length - " + user.getPassword()
                    + " : Minimum " + MIN_PASS_LENGTH + " characters");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("This user : " + user + " already register "
                + storageDao.get(user.getLogin()));
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
    }
}
