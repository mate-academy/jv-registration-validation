package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isNullUser(user)) {
            throw new RegistrationException("User can`t be null.");
        }
        if (isLengthLoginOrPassLess(user.getLogin(), MIN_LOGIN_LENGTH)) {
            throw new RegistrationException("Login can`t be empty or less than 6 characters.");
        }
        if (isLengthLoginOrPassLess(user.getPassword(), MIN_PASSWORD_LENGTH)) {
            throw new RegistrationException("Password can`t be empty or less than 6 characters.");
        }
        if (isAgeLess(user.getAge())) {
            throw new RegistrationException("Age can`t be empty or less than 18 characters.");
        }
        if (isLoginExists(user)) {
            throw new RegistrationException("Login already exists.");
        }
        return storageDao.add(user);
    }

    private boolean isNullUser(User user) {
        return user == null;
    }

    private boolean isLengthLoginOrPassLess(String loginOrPass, int length) {
       return loginOrPass == null || loginOrPass.length() < length;
    }

    private boolean isAgeLess(Integer age) {
        return age == null || age < MIN_AGE;
    }

    private boolean isLoginExists(User userFromDao) {
        return storageDao.get(userFromDao.getLogin()) != null;
    }
}
