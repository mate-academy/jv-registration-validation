package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isValid(user);
        return storageDao.add(user);
    }

    public boolean isValid(User user) {
        if (user == null) {
            throw new ValidationException("You should fill all necessary fields");
        }
        if (user.getId() == null) {
            throw new ValidationException("ID number shouldn't be null");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new ValidationException("User can't be under age 18");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new ValidationException("Your password should have length 6 or more");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new ValidationException("Your login should have length 6 or more");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("Your login is already taken by another user");
        }
        return true;
    }
}
