package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE_REGISTRATION = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User existedUser = storageDao.get(user.getLogin());
        if (existedUser != null) {
            throw new RegistrationException("User with current login already exist");
        }
        boolean isLoginValid = isLengthValid(user.getLogin());
        if (!isLoginValid) {
            throw new RegistrationException("Login isn't correct(should be at least 6 chars");
        }
        boolean isPasswordValid = isLengthValid(user.getPassword());
        if (!isPasswordValid) {
            throw new RegistrationException("Password isn't correct(should be at least 6 chars");
        }
        boolean isAgeValid = isAgeValid(user.getAge());
        if (!isAgeValid) {
            throw new RegistrationException("Age isn't correct(should be at least 18 y.o");
        }
        storageDao.add(user);
        return user;
    }

    private boolean isLengthValid(String data) {
        return data != null && data.length() >= MIN_LENGTH;
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age >= MIN_AGE_REGISTRATION;
    }
}
