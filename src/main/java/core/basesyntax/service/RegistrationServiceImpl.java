package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User newUser = storageDao.get(user.getLogin());
        if (newUser != null) {
            throw new InvalidUserDataException("Sorry but someone steals your name. "
                    + "Go to change your passport");
        }
        if (user.getLogin() == null || user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new InvalidUserDataException("Your name is too short. You need to change it.");
        }
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidUserDataException("Password must contain at least 6 characters."
                    + "For example: \"qwerty\" is a good password");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new InvalidUserDataException("You are too small. Go grow up and than come back");
        }
        storageDao.add(user);
        return user;
    }
}
