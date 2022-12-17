package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE_FOR_USER = 18;
    public static final int MIN_LENGTH_FOR_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    public void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("The field for age is equal null");
        }
        if (user.getAge() < MIN_AGE_FOR_USER) {
            throw new InvalidDataException("Incorrect user age");
        }
    }

    public void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("The field for password is equal null");
        }
        if (user.getPassword().length() < MIN_LENGTH_FOR_PASSWORD) {
            throw new InvalidDataException("Incorrect password, min length 6 characters");
        }
    }

    public void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("The field for login is equal null");
        }
        User user1 = storageDao.get(user.getLogin());
        if (user1 != null) {
            throw new InvalidDataException("This login is not available");
        }
    }

    @Override
    public User register(User user) {
        validateAge(user);
        validatePassword(user);
        validateLogin(user);
        return storageDao.add(user);
    }
}
