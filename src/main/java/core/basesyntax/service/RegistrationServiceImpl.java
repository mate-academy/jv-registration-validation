package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isValid(user);
        return storageDao.add(user);
    }

    public boolean isValid(User user) {
        userCheck(user);
        idCheck(user);
        loginCheck(user);
        passwordCheck(user);
        ageCheck(user);
        return true;
    }

    public void userCheck(User user) {
        if (user == null) {
            throw new ValidationException("You should fill all necessary fields");
        }
    }

    public void idCheck(User user) {
        if (user.getId() == null) {
            throw new ValidationException("ID number shouldn't be null");
        }
    }

    public void loginCheck(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Login shouldn't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("Your login should have length 6 or more");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("Your login is already taken by another user");
        }
    }

    public void passwordCheck(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password shouldn't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Your password should have length 6 or more");
        }
    }

    public void ageCheck(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("Age shouldn't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User can't be under age 18");
        }
    }
}
