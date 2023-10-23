package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User shouldn't be null!");
        }
        if (checkUserAge(user)
                && checkUserNotRegisteredYet(user)
                && checkUserLoginLength(user)
                && checkUserPasswordLength(user)) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean checkUserNotRegisteredYet(User user) {
        for (User registeredUser : Storage.people) {
            if (registeredUser.getLogin() != null && registeredUser.getLogin()
                    .equals(user.getLogin())) {
                throw new InvalidUserDataException("This user is already registered!");
            }
        }
        return true;
    }

    private boolean checkUserLoginLength(User user) {
        if (user.getLogin() != null && user.getLogin().length() >= MIN_LOGIN_LENGTH) {
            return true;
        }
        throw new InvalidUserDataException("Make your login length greater than 6 letters");
    }

    private boolean checkUserPasswordLength(User user) {
        if (user.getPassword() != null && user.getPassword().length() >= MIN_PASSWORD_LENGTH) {
            return true;
        }
        throw new InvalidUserDataException("Your password is to short. 6 signs minimum");
    }

    private boolean checkUserAge(User user) {
        if (user.getAge() != null && user.getAge() >= MIN_AGE) {
            return true;
        }
        throw new InvalidUserDataException("You're too young, go away!");
    }
}
