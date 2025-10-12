package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.throwable.InvalidDataEntry;

public class RegistrationServiceImpl implements RegistrationService {

    static final int MIN_LOGIN_LENGTH = 6;
    static final int MIN_PASSWORD_LENGTH = 6;
    static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataEntry("User is null.");
        }
        userExistCheck(user);
        loginSizeCheck(user);
        ageCheck(user);
        passwordSizeCheck(user);
        return storageDao.add(user);
    }

    @Override
    public boolean userExistCheck(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataEntry("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataEntry("User with such login already exists.");
        }
        return true;
    }

    @Override
    public boolean loginSizeCheck(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataEntry("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataEntry("Login must have at least "
                    + MIN_LOGIN_LENGTH
                    + " characters.");
        }
        return true;
    }

    @Override
    public boolean passwordSizeCheck(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataEntry("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataEntry("Password must have at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters.");
        }
        return true;
    }

    @Override
    public boolean ageCheck(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataEntry("Age can't be null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() < 0) {
            throw new InvalidDataEntry("Age must must at least " + MIN_AGE + " years old.");
        }
        return true;
    }
}
