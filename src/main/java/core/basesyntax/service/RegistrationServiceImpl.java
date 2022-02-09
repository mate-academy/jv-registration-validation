package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LENGTH = 6;
    private final StorageDaoImpl storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        checkIfUserExists(user);
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
        return storageDao.add(checkIfUserAlreadyExists(user));
    }

    private User checkIfUserAlreadyExists (User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already exists");
        }
        return user;
    }

    private void checkIfUserExists(User user) {
        if (user == null) {
            throw new RuntimeException("There is no user");
        }
    }

    public void validateLogin(User user) {
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new RuntimeException("There is no login");
        }
    }

    public void validateAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("Incorrect age, must be above 18 and below 101");
        }
    }

    public void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Incorrect password, password must be at least "
                    + "6 characters");
        }
    }
}
